package com.example.kondus.Kondus_api.modules.item.domain.model

sealed interface CategoryInfo{
    data object Rent: CategoryInfo
    data class Sale(val price:Double): CategoryInfo
}

data class ItemModel(
    val description:String,
    val category:String,
    val categoryInfo: CategoryInfo,
    val ownerId: Long
)
