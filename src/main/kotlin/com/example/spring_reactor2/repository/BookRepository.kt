package com.example.spring_reactor2.repository

import com.example.spring_reactor2.model.Book
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long> {
    fun findByTitleAndAuthor(title: String, author: String): Mono<Book>;
}