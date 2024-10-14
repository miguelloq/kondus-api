package com.example.kondus.Kondus_api.modules.item.data.repository

import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<ItemEntity,String>{
    fun findById(id:Long): ItemEntity?
}