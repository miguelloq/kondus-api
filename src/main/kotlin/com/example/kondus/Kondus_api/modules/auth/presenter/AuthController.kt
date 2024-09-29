package com.example.kondus.Kondus_api.modules.auth.presenter

import com.example.kondus.Kondus_api.modules.auth.infra.service.AuthenticationServiceImpl
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthRequestDto
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/auth")
class AuthController(private val service: AuthenticationServiceImpl) {
    @PostMapping
    fun authenticate(@RequestBody request: AuthRequestDto): AuthResponseDto =
        service.authentication(request)
}