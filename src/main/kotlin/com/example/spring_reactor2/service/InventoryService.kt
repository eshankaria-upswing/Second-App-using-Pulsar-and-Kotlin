package com.example.spring_reactor2.service

import com.example.library.exception.BookDoesNotExistException
import com.example.library.exception.OutOfStockException
import com.example.library.exception.UserDoesNotExistException
import com.example.spring_reactor2.model.Book
import com.example.spring_reactor2.model.Inventory
import com.example.spring_reactor2.repository.BookRepository
import com.example.spring_reactor2.repository.BookRequestRepository
import com.example.spring_reactor2.repository.InventoryRepository
import com.example.spring_reactor2.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class InventoryService(
    private val userService: UserService,
    private val bookService: BookService,
    private val inventoryRepository: InventoryRepository,
    private val bookRequestService: BookRequestService,
) {
    fun findById(id: Long): Mono<Inventory> {
        return inventoryRepository.findById(id)
    }

    fun findByBookId(bookId: Long): Mono<Inventory> {
        return inventoryRepository.findByBookId(bookId);
    }

    fun getAllInventory(): Flux<Inventory> {
        return inventoryRepository.findAll()
    }

    fun reduceBookCount(bookId: Long, userId: Long): Mono<Boolean> {
        return this.findByBookId(bookId)
            .flatMap { inventory ->
                if(inventory.availableCount > 0) {
                    val updatedInventory=inventory.copy(availableCount = inventory.availableCount - 1)
                    bookRequestService.deleteBookRequest(bookId, userId)
                        .then(
                            inventoryRepository.save(updatedInventory)
                                .thenReturn(true)
                        )
                    }
                else
                    bookRequestService.addBookRequest(bookId, userId)
                        .thenReturn(false)
            }
    }

    fun borrowBook(bookId: Long, userId: Long): Mono<Book> {
        return bookService.findBookById(bookId)
            .flatMap { book ->
                userService.getUserById(userId)
                    .flatMap { user ->
                        reduceBookCount(bookId, userId)
                            .thenReturn(book)
                    }
                    .switchIfEmpty(Mono.error(UserDoesNotExistException(userId)))
            }
            .switchIfEmpty(Mono.error(BookDoesNotExistException(bookId)))
    }

    fun returnBook(bookId: Long) : Mono<Inventory>
    {
        return this.findByBookId(bookId)
            .flatMap { inventory ->
                val updatedInventory=inventory.copy(availableCount = inventory.availableCount + 1)
                inventoryRepository.save(updatedInventory)
                    .thenReturn(updatedInventory)
            }
            .switchIfEmpty(Mono.error(BookDoesNotExistException(bookId)))
    }
}