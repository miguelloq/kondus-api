package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel

interface ItemService {
    fun getAllRentsFromUser(email: String): List<ItemModel>
    fun getAllSalesFromUser(email: String): List<ItemModel>
    fun getAllItemsFromUserLocal(email: String): List<Pair<Long, ItemModel>>
}

