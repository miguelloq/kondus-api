package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RentRequestDto
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.SaleRequestDto

interface ItemService {
    fun createSale(dto: SaleRequestDto): Long
    fun createRent(dto: RentRequestDto): Long
    fun getAllRentsFromUser(email: String): List<ItemModel>
    fun getAllSalesFromUser(email: String): List<ItemModel>
    fun getAllItemsFromUser(email: String): List<ItemModel>
    fun getAllItemsFromUserLocal(email: String): List<Pair<Long, ItemModel>>
}

