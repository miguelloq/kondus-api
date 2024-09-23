package com.example.kondus.Kondus_api.modules.auth.infra.service

import com.example.kondus.Kondus_api.modules.auth.infra.config.JwtProperties
import com.example.kondus.Kondus_api.modules.auth.infra.service.UserDetailsServiceImpl
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthDtoRequest
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthDtoResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import com.example.kondus.Kondus_api.modules.auth.infra.service.TokenService
import org.springframework.beans.factory.annotation.Qualifier
import java.util.Date

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    @Qualifier("userDetailsServiceImpl") private val userDetailsServices: UserDetailsServiceImpl,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) {
    fun authentication(authRequest: AuthDtoRequest): AuthDtoResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )

        val user = userDetailsServices.loadUserByUsername(authRequest.email)

        val accessToken = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        return AuthDtoResponse(accessToken)
    }
}