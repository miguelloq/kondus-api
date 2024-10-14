package com.example.kondus.Kondus_api.modules.local.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val cepMaxLength = 8
const val nameMaxLength = 20

@Entity
@Table(name = "locals")
data class LocalEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val street:String,
    val number:Int,
    @Column(length = cepMaxLength) val cep:String,
    @Column(length = nameMaxLength) val name:String,
    val description:String
)