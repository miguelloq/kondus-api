package com.example.kondus.Kondus_api.modules.core.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AuthService {
    fun getEmail():String{
        val authentication = SecurityContextHolder.getContext().authentication
        val email = (authentication.principal as UserDetails).username
        return email;
    }
}