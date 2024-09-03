package com.example.kondus.Kondus_api.modules.auth.service

import com.example.kondus.Kondus_api.modules.core.domain.CoreUser
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetailsService

@Service
class UserDetailsServiceImpl : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        CoreUser("Miguel@gmail.com", "12345").mapToUserDetails()


    private fun CoreUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .build()
}