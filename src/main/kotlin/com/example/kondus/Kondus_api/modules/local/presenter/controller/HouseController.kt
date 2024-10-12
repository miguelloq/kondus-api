package com.example.kondus.Kondus_api.modules.local.presenter.controller

import com.example.kondus.Kondus_api.modules.core.services.AuthService
import com.example.kondus.Kondus_api.modules.local.domain.error.LocalModuleException
import com.example.kondus.Kondus_api.modules.local.domain.model.HouseModel
import com.example.kondus.Kondus_api.modules.local.domain.service.HouseService
import com.example.kondus.Kondus_api.modules.local.presenter.dto.AssociateToUserRequestDto
import com.example.kondus.Kondus_api.modules.local.presenter.dto.CreateHouseRequestDto
import com.example.kondus.Kondus_api.modules.local.presenter.dto.HouseResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/house")
class HouseController(
    private val service: HouseService,
    private val authService: AuthService,
) {
    @PostMapping
    fun create(@RequestBody request: CreateHouseRequestDto): HouseResponseDto = try {
        service
            .create(request)
            .toResponseDto()
    } catch (e: LocalModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    @GetMapping("/all")
    fun getAllFromUser(): List<HouseResponseDto> = try {
        service
            .getHousesFromUser(authService.getEmail())
            .map { it.toResponseDto() }
    } catch (e: LocalModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun associateToUser(request: AssociateToUserRequestDto) = try {
        service
            .assoaciateToUser(
                request,
                authService.getEmail()
            )
    } catch (e: LocalModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }


    fun Pair<Long, HouseModel>.toResponseDto(): HouseResponseDto =
        HouseResponseDto(
            id = first,
            localId = second.localId,
            description = second.description,
            type = second.category.key
        )
}