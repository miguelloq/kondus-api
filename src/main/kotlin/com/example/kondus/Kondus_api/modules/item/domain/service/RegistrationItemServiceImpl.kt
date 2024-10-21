package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import com.example.kondus.Kondus_api.modules.core.services.AuthService
import com.example.kondus.Kondus_api.modules.item.data.entity.RegistrationEntity
import com.example.kondus.Kondus_api.modules.item.data.repository.ItemRepository
import com.example.kondus.Kondus_api.modules.item.data.repository.RegistrationRepository
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RegistrationRequestDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RegistrationItemServiceImpl(
    private val registrationRepo: RegistrationRepository,
    private val itemRepo: ItemRepository,
    private val authService: AuthService
): RegistrationItemService{
    override fun registerItem(dto: RegistrationRequestDto, dealerEmail: String) {
        val itemId = dto.itemId ?: throw ItemModuleException.Validation.MissingField("itemId")

        saveRegistration(itemId,dealerEmail)
    }

    private fun saveRegistration(itemId: Long, dealerEmail: String){
        val dealerUserEntity = authService.getUserByEmail(dealerEmail){ throw ItemModuleException.Data.UserNotFound }
        val itemEntity = itemRepo.findById(itemId) ?: throw ItemModuleException.Data.ItemNotFound


        val itemAlreadyRegistred = registrationRepo.findByItem(itemEntity) == null
        if(itemAlreadyRegistred) throw ItemModuleException.Business.ItemAlreadyRegistred

        val registrationEntity = RegistrationEntity(
            firstTime = LocalDateTime.now(),
            item = itemEntity,
            dealer = dealerUserEntity
        )

        registrationRepo.save(registrationEntity)
    }
}