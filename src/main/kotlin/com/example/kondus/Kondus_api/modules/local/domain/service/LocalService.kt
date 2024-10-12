package com.example.kondus.Kondus_api.modules.local.domain.service

import com.example.kondus.Kondus_api.modules.local.data.entity.LocalEntity
import com.example.kondus.Kondus_api.modules.local.data.repository.LocalRepository
import com.example.kondus.Kondus_api.modules.local.domain.error.LocalModuleException
import com.example.kondus.Kondus_api.modules.local.domain.model.LocalModel
import com.example.kondus.Kondus_api.modules.local.presenter.dto.local.CreateLocalRequestDto
import org.springframework.stereotype.Service

@Service
class LocalService(
    private val repo: LocalRepository,
) {
    fun create(dto: CreateLocalRequestDto): Pair<Long, LocalModel> =
        dto
            .validateToModel()
            .let {
                val databaseId = repo.save(it.toEntity()).id ?: throw LocalModuleException.Unknown
                Pair(databaseId, it)
            }

    fun LocalModel.toEntity(): LocalEntity = LocalEntity(
        street = street,
        number = number.toString(),
        cep = cep.toString(),
        name = name,
        description = description
    )

    fun CreateLocalRequestDto.validateToModel(): LocalModel {
        if (street == null) throw LocalModuleException.Validation.MissingField("street")
        if (number == null) throw LocalModuleException.Validation.MissingField("number")
        if (cep == null) throw LocalModuleException.Validation.MissingField("cep")
        if (name == null) throw LocalModuleException.Validation.MissingField("name")
        if (description == null) throw LocalModuleException.Validation.MissingField("description")

        //TODO Remaining validations

        return LocalModel(
            street = street,
            number = number,
            cep = cep,
            name = name,
            description = description
        )
    }

}