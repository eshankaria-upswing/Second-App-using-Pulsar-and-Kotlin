package com.example.spring_reactor2.controller

import com.example.spring_reactor2.model.Book
import com.example.spring_reactor2.model.Inventory
import com.example.spring_reactor2.service.InventoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping()
class InventoryController (private val inventoryService: InventoryService) {

    @GetMapping("/inventory")
    fun getAllInventory(): Flux<Inventory> {
        return inventoryService.getAllInventory()
    }

    @GetMapping("/inventory/{id}")
    fun findById(@PathVariable id: Long): Mono<ResponseEntity<Inventory>> {
        return inventoryService.findById(id)
        .map { ResponseEntity.ok(it) }
    }

    @GetMapping("/inventory/book/{bookId}")
    fun findByBookId(@PathVariable bookId: Long): Mono<ResponseEntity<Inventory>> {
        return inventoryService.findByBookId(bookId)
            .map { ResponseEntity.ok(it) }
    }

    @PutMapping("borrow/{bookId}/{userId}")
    fun borrowBook(@PathVariable bookId: Long, @PathVariable userId: Long): Mono<ResponseEntity<Book>> {
        return inventoryService.borrowBook(bookId,userId)
        .map { ResponseEntity.ok(it) }
    }

    @PutMapping("return/{bookId}")
    fun returnBook(@PathVariable bookId: Long): Mono<ResponseEntity<Inventory>> {
        return inventoryService.returnBook(bookId)
            .map { ResponseEntity.ok(it) }
    }

}