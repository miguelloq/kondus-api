package com.example.kondus.Kondus_api.modules.local.domain.model

sealed class Category(val key:String){
    data object Apartment:Category("Apartment")
    data object Condominium:Category("Condominium")
}

data class HouseModel (
    val description:String,
    val category: Category,
    val localId:Long
)