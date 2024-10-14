package com.example.kondus.Kondus_api.modules.auth.presenter.dto

import com.example.kondus.Kondus_api.modules.auth.data.entity.Role
import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity

data class UserRequestDto(
    val email: String,
    val password: String,
    val name: String,
)

fun UserRequestDto.toEntity(): UserEntity = UserEntity(
    email = email,
    name = name,
    password = password,
    role = Role.DEFAULT
)