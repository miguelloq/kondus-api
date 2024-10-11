package com.example.kondus.Kondus_api.modules.auth.data.entity

import com.example.kondus.Kondus_api.modules.local.data.entity.HouseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

enum class Role {
    ADMIN, DEFAULT
}

@Table(name = "users")
@Entity(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val email: String,
    val password: String,
    val name:String,
    @Enumerated(EnumType.STRING) val role: Role,
    @ManyToMany(mappedBy = "usuarios") val houses: MutableList<HouseEntity> = mutableListOf()
)