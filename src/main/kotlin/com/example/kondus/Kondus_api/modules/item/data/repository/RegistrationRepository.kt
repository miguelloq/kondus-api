package com.example.kondus.Kondus_api.modules.item.data.repository

import com.example.kondus.Kondus_api.modules.item.data.entity.ItemEntity
import com.example.kondus.Kondus_api.modules.item.data.entity.RegistrationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RegistrationRepository: JpaRepository<RegistrationEntity,String>{
    fun findById(id:Long): RegistrationEntity?
    fun findByItem(item: ItemEntity): List<RegistrationEntity>
}