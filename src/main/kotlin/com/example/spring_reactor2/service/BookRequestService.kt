package com.example.spring_reactor2.service

import com.example.library.exception.BookRequestExists
import com.example.library.exception.OutOfStockException
import com.example.spring_reactor2.model.BookRequest
import com.example.spring_reactor2.repository.BookRepository
import com.example.spring_reactor2.repository.BookRequestRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookRequestService (
    private val bookRequestRepository: BookRequestRepository
) {

    fun getAllBookRequests(): Flux<BookRequest> {
        return bookRequestRepository.findAll()
    }

    fun addBookRequest(bookId: Long, userId: Long) : Mono<Void> {
        return this.findByBookIdAndUserId(bookId, userId)
            .flatMap<Void> { _ ->
                Mono.error(BookRequestExists(bookId, userId))
            }
            .switchIfEmpty(
                bookRequestRepository.save(
                    BookRequest(
                        bookId = bookId,
                        userId = userId
                    )
                )
                    .flatMap { _ ->
                        Mono.error(OutOfStockException(bookId, userId))
                    }
            )
    }

    fun deleteBookRequest(bookId: Long, userId: Long): Mono<Void> {
        return bookRequestRepository.deleteByBookIdAndUserId(bookId, userId)
    }

    fun findByBookIdAndUserId(bookId: Long, userId: Long) : Mono<BookRequest> {
        return bookRequestRepository.findByBookIdAndUserId(bookId, userId)
    }
}