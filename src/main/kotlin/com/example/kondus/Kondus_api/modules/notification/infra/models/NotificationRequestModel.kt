package com.example.kondus.Kondus_api.modules.notification.infra.models

data class NotificationRequestModel(
    val title: String,
    val body: String,
    val topic: String,
    val token: String,
)
