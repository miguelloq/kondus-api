package com.example.kondus.Kondus_api.modules.local.domain.service

import com.example.kondus.Kondus_api.modules.local.data.entity.LocalEntity
import com.example.kondus.Kondus_api.modules.local.data.entity.cepMaxLength
import com.example.kondus.Kondus_api.modules.local.data.entity.nameMaxLength
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
            .toModel()
            .also{it.validate()}
            .let {
                val databaseId = repo.save(it.toEntity()).id ?: throw LocalModuleException.Unknown
                Pair(databaseId, it)
            }

    fun LocalModel.toEntity(): LocalEntity = LocalEntity(
        street = street,
        number = number,
        cep = cep.toString(),
        name = name,
        description = description
    )

    fun CreateLocalRequestDto.toModel(): LocalModel {
        if (street == null) throw LocalModuleException.Validation.MissingField("street")
        if (number == null) throw LocalModuleException.Validation.MissingField("number")
        if (cep == null) throw LocalModuleException.Validation.MissingField("cep")
        if (name == null) throw LocalModuleException.Validation.MissingField("name")
        if (description == null) throw LocalModuleException.Validation.MissingField("description")

        return LocalModel(
            street = street,
            number = number,
            cep = cep,
            name = name,
            description = description
        )
    }

    fun LocalModel.validate(){
        if(number>99999 || 0>number) throw LocalModuleException.Validation.Business("Number of local invalid. Chose a number between a range of 0 and 99999.")
        if(cep.toString().length>cepMaxLength || 0>cep) throw LocalModuleException.Validation.Business("Cep invalid. Cep cannot be negative and cep have a limit digits of 8.")
        if(name.length>=nameMaxLength) throw LocalModuleException.Validation.Business("Name invalid. Name size cannot be more than 20 letters.")
    }
}