package com.example.kondus.Kondus_api.modules.local.domain.service

import com.example.kondus.Kondus_api.modules.local.data.entity.HouseEntity
import com.example.kondus.Kondus_api.modules.local.data.entity.LocalEntity
import com.example.kondus.Kondus_api.modules.local.data.repository.HouseRepository
import com.example.kondus.Kondus_api.modules.local.data.repository.LocalRepository
import com.example.kondus.Kondus_api.modules.local.domain.error.LocalModuleException
import com.example.kondus.Kondus_api.modules.local.domain.model.Category
import com.example.kondus.Kondus_api.modules.local.domain.model.HouseModel
import com.example.kondus.Kondus_api.modules.local.presenter.dto.CreateHouseRequestDto
import org.springframework.stereotype.Service

typealias LocalId = Long

@Service
class HouseService(private val repo: HouseRepository,private val localRepo: LocalRepository) {
    fun create(dto: CreateHouseRequestDto): Pair<Long,HouseModel> =
        dto
            .validateToModel()
            .let{
                val (databaseId,entity) = it.toEntity()
                repo.save(entity)
                Pair(databaseId,it)
            }


    fun LocalId.toLocalEntity(): LocalEntity =
        localRepo
            .findById(this)
            ?: throw LocalModuleException.Data.LocalNotFound("The location id that was used has no record in the database.")

    fun HouseModel.toEntity(): Pair<Long,HouseEntity> =
        localId
            .toLocalEntity()
            .let{
                Pair(
                    it.id ?: throw LocalModuleException.Unknown,
                    HouseEntity(
                        description = description,
                        type = category.key,
                        local = it
                    ))}

    fun CreateHouseRequestDto.validateToModel(): HouseModel{
        if(description==null) throw LocalModuleException.Validation.MissingField("description")
        if(type==null) throw LocalModuleException.Validation.MissingField("type")
        if(localId==null) throw LocalModuleException.Validation.MissingField("localId")

        if(description.isEmpty()) throw LocalModuleException.Validation.Business("Description cannot be empty.")

        val category = when(type){
            "Apartment" -> Category.Apartment
            "Condominium" -> Category.Condominium
            else -> throw LocalModuleException.Validation.Business("The only valid options for type is Apartment or Condominium.")
        }
        return HouseModel(description,category,localId)
    }
}
