package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RegistrationRequestDto

interface RegistrationItemService {
    fun registerItem(dto: RegistrationRequestDto,dealerEmail: String)
}