package com.example.kondus.Kondus_api.modules.item.presenter.controller

import com.example.kondus.Kondus_api.modules.core.services.AuthService
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.domain.service.ItemService
import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.ItemResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/item")
class ItemController(
    private val service: ItemService,
    private val authService: AuthService,
) {
    @GetMapping
    fun getAllItemsFromUser(): List<ItemResponseDto> = try {
        val email = authService.getEmail()
        val items = service.getAllSalesFromUser(email) + service.getAllRentsFromUser(email)

        items.map { it.toResponse() }

    } catch (e: ItemModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    @GetMapping("/rent")
    fun getAllRentsFromUser(): List<ItemResponseDto> = try {
        val email = authService.getEmail()
        service
            .getAllRentsFromUser(email)
            .map { it.toResponse() }

    } catch (e: ItemModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    @GetMapping("/sale")
    fun getAllSalesFromUser(): List<ItemResponseDto> = try {
        service
            .getAllSalesFromUser(authService.getEmail())
            .map { it.toResponse() }

    } catch (e: ItemModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    @GetMapping("/local")
    fun getAllItemsFromLocal(): List<ItemResponseDto> = try {
        service
            .getAllItemsFromUserLocal(authService.getEmail())
            .map { (itemId, item) -> item.toResponse(itemId) }
    } catch (e: ItemModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }


    fun ItemModel.toResponse(id: Long? = null): ItemResponseDto =
        ItemResponseDto(
            description = description,
            id = id
        )
}