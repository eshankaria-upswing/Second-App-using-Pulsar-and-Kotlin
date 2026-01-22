package com.example.spring_reactor2.repository

import com.example.spring_reactor2.model.Inventory
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface InventoryRepository : ReactiveCrudRepository<Inventory, Long> {
    fun findByBookId(bookId: Long): Mono<Inventory>
    fun deleteByBookId(bookId: Long): Mono<Void>
}