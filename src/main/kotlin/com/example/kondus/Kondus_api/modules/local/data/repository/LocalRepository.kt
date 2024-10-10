package com.example.kondus.Kondus_api.modules.local.data.repository

import com.example.kondus.Kondus_api.modules.local.data.entity.LocalEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LocalRepository: JpaRepository<LocalEntity, String> {
    fun findById(id: Long): LocalEntity?
}