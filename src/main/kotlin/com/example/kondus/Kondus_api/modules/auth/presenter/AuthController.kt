package com.example.kondus.Kondus_api.modules.auth.presenter

import com.example.kondus.Kondus_api.modules.auth.infra.service.AuthenticationServiceImpl
import com.example.kondus.Kondus_api.modules.auth.infra.service.UserService
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthRequestDto
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthResponseDto
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.UserRequestDto
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.UserResponseDto
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.toEntity
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.toResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/auth")
class AuthController(
    private val service: AuthenticationServiceImpl,
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequestDto): AuthResponseDto =
        service
            .authentication(request)

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequestDto): UserResponseDto =
        request
            .toEntity()
            .let { userService.createUser(it) }
            ?.toResponse()
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Cannot create a user"
            )
}