package com.example.spring_reactor2.repository

import com.example.spring_reactor2.model.BookRequest
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface BookRequestRepository : ReactiveCrudRepository<BookRequest, Long> {
    fun findByBookIdAndUserId(bookId: Long, userId: Long): Mono<BookRequest>
//    fun deleteByBookIdAndUserId(bookId: Long, userId: Long): Mono<Void>
    @Query(
        """
        DELETE FROM book_requests
        WHERE book_id = :bookId
          AND user_id = :userId
        """
    )
    fun deleteByBookIdAndUserId(
        bookId: Long,
        userId: Long
    ): Mono<Void>
}