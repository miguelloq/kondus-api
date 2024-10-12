package com.example.kondus.Kondus_api.modules.local.presenter.dto.local

data class CreateLocalRequestDto(
    val street: String?,
    val number: Int?,
    val cep: Int?,
    val name: String?,
    val description: String?,
)