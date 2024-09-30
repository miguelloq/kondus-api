package com.example.kondus.Kondus_api.modules.notification.infra.services

import com.example.kondus.Kondus_api.modules.notification.infra.models.NotificationRequestModel
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.gson.GsonBuilder
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ExecutionException
import com.google.firebase.messaging.Notification

@Service
class FCMService {

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageToToken(request: NotificationRequestModel) {
        val message = getPreconfiguredMessageToToken(request)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonOutput = gson.toJson(message)
        val response = sendAndGetResponse(message)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    private fun sendAndGetResponse(message: Message): String {
        return FirebaseMessaging.getInstance().sendAsync(message).get()
    }

    private fun getAndroidConfig(topic: String): AndroidConfig {
        return AndroidConfig.builder()
            .setTtl(Duration.ofMinutes(2).toMillis())
            .setCollapseKey(topic)
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(AndroidNotification.builder().setTag(topic).build())
            .build()
    }

    private fun getApnsConfig(topic: String): ApnsConfig {
        return ApnsConfig.builder()
            .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build())
            .build()
    }

    private fun getPreconfiguredMessageToToken(request: NotificationRequestModel): Message {
        return getPreconfiguredMessageBuilder(request).setToken(request.token).build()
    }

    private fun getPreconfiguredMessageBuilder(request: NotificationRequestModel): Message.Builder {
        val androidConfig = getAndroidConfig(request.topic)
        val apnsConfig = getApnsConfig(request.topic)
        val notification = Notification.builder()
            .setTitle(request.title)
            .setBody(request.body)
            .build()
        return Message.builder()
            .setApnsConfig(apnsConfig)
            .setAndroidConfig(androidConfig)
            .setNotification(notification)
    }
}
