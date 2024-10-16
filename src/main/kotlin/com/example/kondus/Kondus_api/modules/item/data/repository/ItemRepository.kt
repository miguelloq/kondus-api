package com.example.kondus.Kondus_api.modules.item.data.repository

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<ItemEntity, String> {
    fun findById(id: Long): ItemEntity?
    fun findAllByUser(user: UserEntity): List<ItemEntity>
}