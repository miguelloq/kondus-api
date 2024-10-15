package com.example.kondus.Kondus_api.modules.local.domain.model

sealed class Category(val key:String){
    data object Apartment:Category("Apartment")
    data object Condominium:Category("Condominium")
}

data class LocalModel(
    val street: String,
    val number: Int,
    val cep: Int,
    val name: String,
    val description: String,
    val category: Category
)
