package com.example.kondus.Kondus_api.modules.local.domain.error

sealed class LocalModuleException(override val message:String):Exception(message) {

    sealed class Validation(override val message:String): LocalModuleException(message){
        data class MissingField(val fieldName:String): Validation("The $fieldName is obrigatory.")
        data class Business(override val message:String): Validation(message)
    }

    sealed class Data(override val message:String): LocalModuleException(message) {
        data object LocalNotFound: Data("The Location id that was used has no record in the database.")
        data object UserNotFound: Data("The User id that was used has no record in the database.")
    }

    data object Unknown: LocalModuleException("Unknown Error")
}