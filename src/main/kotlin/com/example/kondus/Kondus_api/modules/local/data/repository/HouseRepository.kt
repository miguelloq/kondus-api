package com.example.kondus.Kondus_api.modules.local.data.repository

import com.example.kondus.Kondus_api.modules.local.data.entity.HouseEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HouseRepository: JpaRepository<HouseEntity, String> {
    fun findById(id: Long): HouseEntity?
}