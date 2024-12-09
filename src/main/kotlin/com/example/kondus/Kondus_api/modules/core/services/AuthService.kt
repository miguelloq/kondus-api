package com.example.kondus.Kondus_api.modules.core.services

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthService(
    val userRepo: UserRepository
) {
    fun getEmail():String{
        val authentication = SecurityContextHolder.getContext().authentication
        val email = (authentication.principal as UserDetails).username
        return email;
    }

    fun getUserByEmail(
        email:String,
        userNotFoundFunc: () -> Nothing
    ): UserEntity =
        userRepo
            .findByEmail(email)
            .let{ it ?: userNotFoundFunc() }
}