package com.example.kondus.Kondus_api.modules.local.domain.service

import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import com.example.kondus.Kondus_api.modules.local.data.entity.HouseEntity
import com.example.kondus.Kondus_api.modules.local.data.entity.LocalEntity
import com.example.kondus.Kondus_api.modules.local.data.repository.HouseRepository
import com.example.kondus.Kondus_api.modules.local.data.repository.LocalRepository
import com.example.kondus.Kondus_api.modules.local.domain.error.LocalModuleException
import com.example.kondus.Kondus_api.modules.local.domain.model.Category
import com.example.kondus.Kondus_api.modules.local.domain.model.HouseModel
import com.example.kondus.Kondus_api.modules.local.presenter.dto.house.AssociateToUserRequestDto
import com.example.kondus.Kondus_api.modules.local.presenter.dto.house.CreateHouseRequestDto
import org.springframework.stereotype.Service

typealias LocalId = Long

@Service
class HouseService(
    private val repo: HouseRepository,
    private val localRepo: LocalRepository,
    private val userRepo: UserRepository,
) {
    fun create(dto: CreateHouseRequestDto): Pair<Long, HouseModel> =
        dto
            .validateToModel()
            .let {
                val databaseId = repo.save(it.toEntity()).id ?: throw LocalModuleException.Unknown
                Pair(databaseId, it)
            }

    fun getHousesFromUser(email: String): List<Pair<Long, HouseModel>> =
        userRepo
            .findByEmail(email)
            .let{it ?: throw LocalModuleException.Data.UserNotFound}
            .houses
            .map {
                Pair(
                    it.id ?: throw LocalModuleException.Unknown,
                    it.toModel()
                )
            }

    fun associateToUser(dto: AssociateToUserRequestDto, email: String) {
        val userEntity = userRepo.findByEmail(email) ?: throw LocalModuleException.Data.UserNotFound

        val houseId = dto.houseId ?: throw LocalModuleException.Validation.MissingField("houseId")
        val houseEntity = repo.findById(houseId) ?: throw LocalModuleException.Data.LocalNotFound

        if(userEntity.houses.contains(houseEntity)) throw LocalModuleException.Data.UserAlreadyIsAssociateWithLocal

        houseEntity.users.add(userEntity)

        repo.save(houseEntity)
    }

    fun HouseEntity.toModel() =
        HouseModel(
            description = description,
            localId = local.id ?: throw LocalModuleException.Unknown,
        )

    fun LocalId.toLocalEntity(): LocalEntity =
        localRepo
            .findById(this)
            ?: throw LocalModuleException.Data.LocalNotFound

    fun HouseModel.toEntity(): HouseEntity =
        localId
            .toLocalEntity()
            .let {
                HouseEntity(
                    description = description,
                    local = it
                )
            }
    
    fun CreateHouseRequestDto.validateToModel(): HouseModel {
        if (description == null) throw LocalModuleException.Validation.MissingField("description")
        if (localId == null) throw LocalModuleException.Validation.MissingField("localId")

        if (description.isEmpty()) throw LocalModuleException.Validation.Business("Description cannot be empty.")

        return HouseModel(description, localId)
    }
}
