package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel

interface GetItemService {
    fun allRentsFromUser(email: String): List<ItemModel>
    fun allSalesFromUser(email: String): List<ItemModel>
    fun allFromUser(email: String): List<ItemModel>
    fun allFromUserLocal(email: String): List<Pair<Long, ItemModel>>
}

