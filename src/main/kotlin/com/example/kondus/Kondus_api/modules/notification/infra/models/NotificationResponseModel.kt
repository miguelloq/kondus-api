package com.example.kondus.Kondus_api.modules.notification.infra.models

import org.springframework.http.HttpStatus

data class NotificationResponseModel(
    val status: HttpStatus,
    val message: String,
)