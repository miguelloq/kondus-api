package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import com.example.kondus.Kondus_api.modules.item.data.repository.ItemRepository
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.domain.model.CategoryInfo
import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RentRequestDto
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.SaleRequestDto
import com.example.kondus.Kondus_api.modules.local.data.repository.HouseRepository


class ItemServiceImpl(
    val repo: ItemRepository,
    val userRepo: UserRepository,
    val houseRepo: HouseRepository
) : ItemService {

    override fun createSale(dto: SaleRequestDto): Long {
        TODO("Not yet implemented")
    }

    override fun createRent(dto: RentRequestDto): Long {
        TODO("Not yet implemented")
    }

    override fun getAllRentsFromUser(email: String): List<ItemModel> =
        getAllItemsFromUser(email)
            .filter { it.categoryInfo is CategoryInfo.Rent }


    override fun getAllSalesFromUser(email: String): List<ItemModel> =
        getAllItemsFromUser(email)
            .filter { it.categoryInfo is CategoryInfo.Sale }

    override fun getAllItemsFromUser(email: String): List<ItemModel> =
        userRepo
            .findByEmail(email)
            .let { it ?: throw ItemModuleException.Data.UserNotFound }
            .let { repo.findAllByUser(it) }
            .map { it.toModel() }

    override fun getAllItemsFromUserLocal(email: String): List<Pair<Long, ItemModel>> =
        userRepo
            .findByEmail(email)
            .let { it ?: throw ItemModuleException.Data.UserNotFound }
            .houses
            .map { it.local }
            .flatMap{ houseRepo.findAllByLocal(it) }
            .flatMap{ it.users }
            .flatMap{ repo.findAllByUser(it) }
            .map { Pair(it.id ?: throw ItemModuleException.Unknown,it.toModel()) }


    fun ItemEntity.toModel(): ItemModel = ItemModel(
        description = description,
        ownerId = user.id ?: throw ItemModuleException.Unknown,
        categoryInfo = if(price==null) CategoryInfo.Rent else CategoryInfo.Sale(price)
    )
}