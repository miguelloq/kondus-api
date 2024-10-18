package com.example.kondus.Kondus_api.modules.item.presenter.controller

import com.example.kondus.Kondus_api.modules.core.services.AuthService
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.domain.service.ItemService
import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.ItemResponseDto
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.RentRequestDto
import com.example.kondus.Kondus_api.modules.item.presenter.dto.item.SaleRequestDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/item")
class ItemController(
    private val service: ItemService,
    private val authService: AuthService,
) {

    private fun <T> itemCatching(block:()->T) = try {
        block()
    } catch (e: ItemModuleException) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
    } catch (e: Exception) {
        throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    @PostMapping("/rent")
    fun createRent(@RequestBody request: RentRequestDto): Long = itemCatching {
        service
            .createRent(request)
    }

    @PostMapping("/sale")
    fun createSale(@RequestBody request: SaleRequestDto): Long = itemCatching {
        service
            .createSale(request)
    }

    @GetMapping
    fun getAllItemsFromUser(): List<ItemResponseDto> = itemCatching {
        val email = authService.getEmail()
        val items = service.getAllItemsFromUser(email)

        items.map { it.toResponse() }
    }

    @GetMapping("/rent")
    fun getAllRentsFromUser(): List<ItemResponseDto>  = itemCatching {
        val email = authService.getEmail()
        service
            .getAllRentsFromUser(email)
            .map { it.toResponse() }
    }

    @GetMapping("/sale")
    fun getAllSalesFromUser(): List<ItemResponseDto> = itemCatching {
        service
            .getAllSalesFromUser(authService.getEmail())
            .map { it.toResponse() }
    }

    @GetMapping("/local")
    fun getAllItemsFromLocal(): List<ItemResponseDto> = itemCatching {
        service
            .getAllItemsFromUserLocal(authService.getEmail())
            .map { (itemId, item) -> item.toResponse(itemId) }
    }

    fun ItemModel.toResponse(id: Long? = null): ItemResponseDto =
        ItemResponseDto(
            description = description,
            id = id
        )
}