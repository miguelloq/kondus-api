package com.example.kondus.Kondus_api.modules.item.domain.model

import java.time.LocalDateTime

data class RegistrationModel(
    val firstTime: LocalDateTime,
    val itemId: Long,
    val userDealerId: Long,
)