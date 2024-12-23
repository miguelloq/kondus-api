package com.example.kondus.Kondus_api.modules.local.presenter.controller

import com.example.kondus.Kondus_api.modules.local.domain.error.LocalModuleException
import com.example.kondus.Kondus_api.modules.local.domain.model.LocalModel
import com.example.kondus.Kondus_api.modules.local.domain.service.LocalService
import com.example.kondus.Kondus_api.modules.local.presenter.dto.local.CreateLocalRequestDto
import com.example.kondus.Kondus_api.modules.local.presenter.dto.local.CreateLocalResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/local")
class LocalController(
    private val service: LocalService,
) {
    @PostMapping
    fun create(@RequestBody request: CreateLocalRequestDto): CreateLocalResponseDto = try {
        service
            .create(request)
            .toCreateResponseDto()
    } catch (e: LocalModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    fun Pair<Long, LocalModel>.toCreateResponseDto() = CreateLocalResponseDto(
        id = first,
        name = second.name
    )
}