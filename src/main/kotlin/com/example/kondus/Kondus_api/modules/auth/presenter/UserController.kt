package com.example.kondus.Kondus_api.modules.auth.presenter

import com.example.kondus.Kondus_api.modules.auth.data.entity.Role
import com.example.kondus.Kondus_api.modules.auth.data.entity.UserEntity
import com.example.kondus.Kondus_api.modules.auth.infra.service.UserService
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.UserDtoRequest
import com.example.kondus.Kondus_api.modules.auth.presenter.dto.UserDtoResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/user")
class UserController(private val service: UserService) {

    @PostMapping
    fun create(@RequestBody request: UserDtoRequest): UserDtoResponse =
        request
            .toEntity()
            .let { service.createUser(it) }
            ?.toResponse()
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Cannot create a user"
            )

    @GetMapping
    fun listAll(): List<UserDtoResponse> =
        service
            .listAll()
            .map { it.toResponse() }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserDtoResponse =
        service
            .findById(id)
            ?.toResponse()
            ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Cannot find a user"
            )


    fun UserDtoRequest.toEntity(): UserEntity = UserEntity(
        email = email,
        password = password,
        role = Role.DEFAULT
    )
    
    fun UserEntity.toResponse(): UserDtoResponse = UserDtoResponse(
        id = id ?: 0,
        email = email
    )
}