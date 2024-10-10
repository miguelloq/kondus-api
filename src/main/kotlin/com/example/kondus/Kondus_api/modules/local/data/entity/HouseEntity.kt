package com.example.kondus.Kondus_api.modules.local.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "houses")
data class HouseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne @JoinColumn(name = "local_id", nullable = false) val local: LocalEntity,
    val description:String
)
