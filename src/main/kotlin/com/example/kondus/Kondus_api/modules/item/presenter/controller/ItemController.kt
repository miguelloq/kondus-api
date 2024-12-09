package com.example.kondus.Kondus_api.modules.item.presenter.controller

import com.example.kondus.Kondus_api.modules.core.services.AuthService
import com.example.kondus.Kondus_api.modules.item.domain.error.ItemModuleException
import com.example.kondus.Kondus_api.modules.item.domain.service.GetItemService
import com.example.kondus.Kondus_api.modules.item.domain.model.ItemModel
import com.example.kondus.Kondus_api.modules.item.domain.service.CreateItemService
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
    private val getItemService: GetItemService,
    private val createItemService: CreateItemService,
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
        createItemService
            .createRent(
                dto = request,
                ownerEmail = authService.getEmail()
            )
    }

    @PostMapping("/sale")
    fun createSale(@RequestBody request: SaleRequestDto): Long = itemCatching {
        createItemService
            .createSale(
                dto = request,
                ownerEmail = authService.getEmail()
            )
    }

    @GetMapping
    fun getAllItemsFromUser(): List<ItemResponseDto> = itemCatching {
        val email = authService.getEmail()
        val items = getItemService.allFromUser(email)

        items.map { it.toResponse() }
    }

    @GetMapping("/rent")
    fun getAllRentsFromUser(): List<ItemResponseDto>  = itemCatching {
        val email = authService.getEmail()
        getItemService
            .allRentsFromUser(email)
            .map { it.toResponse() }
    }

    @GetMapping("/sale")
    fun getAllSalesFromUser(): List<ItemResponseDto> = itemCatching {
        getItemService
            .allSalesFromUser(authService.getEmail())
            .map { it.toResponse() }
    }

    @GetMapping("/local")
    fun getAllItemsFromLocal(): List<ItemResponseDto> = itemCatching {
        getItemService
            .allFromUserLocal(authService.getEmail())
            .map { (itemId, item) -> item.toResponse(itemId) }
    }

    fun ItemModel.toResponse(id: Long? = null): ItemResponseDto =
        ItemResponseDto(
            description = description,
            id = id
        )
}