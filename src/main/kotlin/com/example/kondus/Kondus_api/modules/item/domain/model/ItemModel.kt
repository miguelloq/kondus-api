package com.example.kondus.Kondus_api.modules.item.domain.model

sealed class CategoryInfo(val key: String) {
    data object Rent : CategoryInfo("Rent")
    data class Sale(val price: Double) : CategoryInfo("Sale")
}

data class ItemModel(
    val description: String,
    val categoryInfo: CategoryInfo,
    val ownerId: Long,
)
