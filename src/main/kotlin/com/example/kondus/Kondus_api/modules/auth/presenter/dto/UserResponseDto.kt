package com.example.kondus.Kondus_api.modules.auth.presenter.dto

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity

data class UserResponseDto(
    val id: Long,
    val email: String
)

fun UserEntity.toResponse(): UserResponseDto = UserResponseDto(
    id = id ?: 0,
    email = email
)