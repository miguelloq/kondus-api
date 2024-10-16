package com.example.kondus.Kondus_api.modules.item.domain.service

import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import com.example.kondus.Kondus_api.modules.item.data.repository.ItemRepository
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel


class ItemServiceImpl(
    val repo: ItemRepository,
    val userRepo: UserRepository,
) : ItemService {
    override fun getAllRentsFromUser(email: String): List<ItemModel> =
        userRepo
            .findByEmail(email)
            .let { it ?: throw ItemModuleException.Data.UserNotFound }
            .let { repo.findAllByUser(it) }
            .toModels()


    override fun getAllSalesFromUser(email: String): List<ItemModel> {
        TODO("Not yet implemented")
    }

    override fun getAllItemsFromUserLocal(email: String): List<Pair<Long, ItemModel>> {
        TODO("Not yet implemented")
    }

    fun List<ItemEntity>.toModels(): List<ItemModel> = map { entity ->
        TODO("Not yet implemented")
    }
}