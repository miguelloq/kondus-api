package com.example.kondus.Kondus_api.modules.auth.infra.service

import com.example.kondus.Kondus_api.modules.auth.infra.config.JwtProperties
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthRequestDto
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.AuthResponseDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Qualifier
import java.util.Date

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    @Qualifier("userDetailsServiceImpl") private val userDetailsServices: UserDetailsServiceImpl,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) {
    fun authentication(authRequest: AuthRequestDto): AuthResponseDto {
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

        return AuthResponseDto(accessToken)
    }
}