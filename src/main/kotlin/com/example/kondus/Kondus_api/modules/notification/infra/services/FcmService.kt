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
import com.google.firebase.messaging.Notification

@Service
class FCMService {
    fun sendMessageToToken(request: NotificationRequestModel) = try{
        request
            .toMessage()
            .also{print("Sending message: ${it.toGson()}")}
            .let{sendMessage(it)}
            .also{print("Send operation operation response:$it")}
        Unit
    } catch(e:Exception){
        print("Fail to send $request \n because $e")
    }

    private fun sendMessage(message: Message) : String =
        FirebaseMessaging
            .getInstance()
            .sendAsync(message)
            .get()

    private fun Message.toGson() : String  =
        GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(this)

    private fun NotificationRequestModel.toMessage() : Message =
        Message
            .builder()
            .setApnsConfig(getApnsConfig(topic))
            .setAndroidConfig(getAndroidConfig(topic))
            .setNotification(toNotification())
            .setToken(token)
            .build()

    private fun NotificationRequestModel.toNotification() : Notification =
        Notification
            .builder()
            .setTitle(title)
            .setBody(body)
            .build()

    private fun getAndroidConfig(topic: String): AndroidConfig =
        AndroidConfig
            .builder()
            .setTtl(Duration.ofMinutes(2).toMillis())
            .setCollapseKey(topic)
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(AndroidNotification
                .builder()
                .setTag(topic)
                .build())
            .build()


    private fun getApnsConfig(topic: String): ApnsConfig =
        ApnsConfig
            .builder()
            .setAps(Aps
                    .builder()
                    .setCategory(topic)
                    .setThreadId(topic)
                    .build()
            )
            .build()
}
