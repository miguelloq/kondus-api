package com.example.kondus.Kondus_api.modules.local.domain.error

sealed class LocalModuleException(override val message:String):Exception(message) {

    sealed class ValidationField(override val message:String): LocalModuleException(message){
        data class Missing(override val message:String): ValidationField(message)
        data class Business(override val message:String): ValidationField(message)
    }

    sealed class Data(override val message:String): LocalModuleException(message) {
        data class LocalNotFound(override val message:String): Data(message)
    }

    data object Unknown: LocalModuleException("Unknown Error")
}