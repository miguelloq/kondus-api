package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import com.example.kondus.Kondus_api.modules.item.data.repository.ItemRepository
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.domain.model.CategoryInfo
import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RentRequestDto
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.SaleRequestDto

class CreateItemServiceImpl(
    val repo: ItemRepository,
    val userRepo: UserRepository,
): CreateItemService {

    override fun createSale(dto: SaleRequestDto, ownerEmail: String): Long =
        ownerEmail
            .let{userRepo.findByEmail(it) ?: throw ItemModuleException.Data.UserNotFound }
            .let{
                val model = dto.toModel(it.id ?: throw ItemModuleException.Unknown)
                model.validate()
                val entity = model.toEntity(it)
                repo.save(entity)
            }
            .let{it.id ?: throw ItemModuleException.Unknown}

    override fun createRent(dto: RentRequestDto, ownerEmail: String): Long =
        ownerEmail
            .let{userRepo.findByEmail(it) ?: throw ItemModuleException.Data.UserNotFound }
            .let{
                val model = dto.toModel(it.id ?: throw ItemModuleException.Unknown)
                model.validate()
                val entity = model.toEntity(it)
                repo.save(entity)
            }
            .let{it.id ?: throw ItemModuleException.Unknown}

    fun SaleRequestDto.toModel(ownerId: Long) = ItemModel(
        description = description ?: throw ItemModuleException.Validation.MissingField("description"),
        categoryInfo = CategoryInfo.Sale(price ?: throw ItemModuleException.Validation.MissingField("price")),
        ownerId = ownerId
    )

    fun RentRequestDto.toModel(ownerId: Long) = ItemModel(
        description = description ?: throw ItemModuleException.Validation.MissingField("description"),
        categoryInfo = CategoryInfo.Rent,
        ownerId = ownerId
    )

    fun ItemModel.toEntity(user: UserEntity) = ItemEntity(
        description = description,
        category = categoryInfo.key,
        user = user,
        price = when(categoryInfo){
            is CategoryInfo.Sale -> categoryInfo.price
            is CategoryInfo.Rent -> null
        }
    )

    fun ItemModel.validate() = also{ model ->
        //TODO Bussiness validation in model
    }
}