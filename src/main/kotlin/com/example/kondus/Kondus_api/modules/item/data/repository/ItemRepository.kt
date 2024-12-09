package com.example.kondus.Kondus_api.modules.item.data.repository

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ItemRepository : JpaRepository<ItemEntity, String> {
    fun findById(id: Long): ItemEntity?
    fun findAllByUser(user: UserEntity): List<ItemEntity>

    @Query("SELECT i FROM ItemEntity i " +
            "WHERE i.user = :user AND i.category = 'Rent' ")
    fun findRentsByUser(user: UserEntity): List<ItemEntity>

    @Query("""
    SELECT i FROM ItemEntity i
    WHERE i.user  = :user
    AND i.category = 'Sale'
    """)
    fun findSalesByUser(user: UserEntity): List<ItemEntity>

//    @Query("""
//    SELECT i.id, i FROM ItemEntity i
//    JOIN i.user u
//    JOIN u.houses h
//    JOIN h.local l
//    WHERE u = :user
//    """)
//    fun findFromUserLocal(user: UserEntity): List<ItemEntity>
}