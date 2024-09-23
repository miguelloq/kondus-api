package com.example.kondus.Kondus_api.modules.auth.infra.service

import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.auth.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repo: UserRepository,
    private val encoder: PasswordEncoder
) {
    fun createUser(user: UserEntity): UserEntity? =
        repo.findByEmail(user.email).let {
            if (it != null) null
            else repo.save(
                user.copy(
                    password = encoder.encode(user.password)
                )
            ).let { user }
        }

    fun findById(id: Long): UserEntity? =
        repo
            .findById(id)

    fun listAll(): List<UserEntity> =
        repo.findAll()
}