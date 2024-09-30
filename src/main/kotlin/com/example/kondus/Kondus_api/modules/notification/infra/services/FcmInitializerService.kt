package com.example.kondus.Kondus_api.modules.notification.infra.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.jsonwebtoken.io.IOException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FcmInitializerService(
    @Value("\${app.firebase-configuration-file}") private val firebaseConfigPath: String,
) {
    @PostConstruct
    fun initialize() {
        try {
            val options = FirebaseOptions
                .builder()
                .setCredentials(
                    GoogleCredentials
                        .fromStream(ClassPathResource(firebaseConfigPath).inputStream)
                )
                .build()
            if (FirebaseApp.getApps().isEmpty()) FirebaseApp.initializeApp(options)
        } catch (e: IOException) {
            print(e)
        }
    }
}