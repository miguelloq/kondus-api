package com.example.kondus.Kondus_api.modules.item.data.entity

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name="items")
data class ItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val description:String,
    val category:String,
    val price: Double?,
    @ManyToOne @JoinColumn(name = "user_id", nullable = false) val user: UserEntity
)