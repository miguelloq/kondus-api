package com.example.kondus.Kondus_api.modules.auth.data.repository

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
    fun findById(id: Long): UserEntity?
}