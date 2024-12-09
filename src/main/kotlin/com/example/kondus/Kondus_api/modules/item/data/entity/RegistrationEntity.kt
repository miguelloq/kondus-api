package com.example.kondus.Kondus_api.modules.item.data.entity

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "registrations")
data class RegistrationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(name = "first_time", nullable = false) val firstTime: LocalDateTime,
    @ManyToOne @JoinColumn(name = "item_id", nullable = false) val item: ItemEntity,
    @ManyToOne @JoinColumn(name = "dealer_id", nullable = false) val dealer: UserEntity,
)