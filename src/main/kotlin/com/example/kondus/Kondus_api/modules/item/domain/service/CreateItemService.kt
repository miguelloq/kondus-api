package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RentRequestDto
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.SaleRequestDto

interface CreateItemService {
    fun createSale(dto: SaleRequestDto, ownerEmail: String): Long
    fun createRent(dto: RentRequestDto, ownerEmail: String): Long
}