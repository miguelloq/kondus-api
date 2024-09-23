package com.example.kondus.Kondus_api.modules.auth.infra.service

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class UserDetailsServiceImpl(private val repo: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        repo
            .findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")

    fun UserEntity.mapToUserDetails(): UserDetails =
        User.builder()
            .username(email)
            .password(password)
            .build()
}