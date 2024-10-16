package com.example.kondus.Kondus_api.modules.item.domain.error

sealed class ItemModuleException(override val message: String) : Exception(message) {

    sealed class Data(override val message: String) : ItemModuleException(message) {
        data object UserNotFound : Data("The User id that was used has no record in the database.")
    }

    data object Unknown : ItemModuleException("Unknown Error")

}