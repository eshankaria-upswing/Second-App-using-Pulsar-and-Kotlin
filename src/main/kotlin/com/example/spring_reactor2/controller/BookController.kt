package com.example.spring_reactor2.controller

import com.example.spring_reactor2.dto.AddBookRequest
import com.example.spring_reactor2.model.Book
import com.example.spring_reactor2.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController (val bookService: BookService) {

    @GetMapping
    fun findAllBooks(): Flux<Book> {
        return bookService.getAllBooks()
    }

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: Long): Mono<ResponseEntity<Book>> {
        return bookService.findBookById(bookId)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping("/{title}/{author}")
    fun findBookByTitleAndAuthor(@PathVariable title: String, @PathVariable author: String): Mono<ResponseEntity<Book>> {
        return bookService.findByBooksByTitleAndAuthor(title,author)
        .map { ResponseEntity.ok(it) }
        .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun addBook(@RequestBody bookRequest: AddBookRequest): Mono<ResponseEntity<Book>> {
        return bookService.addBook(bookRequest)
        .map { ResponseEntity.ok(it) }
    }

    @DeleteMapping("{bookId}")
    fun deleteBookById(@PathVariable bookId: Long): Mono<ResponseEntity<Void>> {
        return bookService.deleteBook(bookId)
            .map { deleted ->
                if(deleted)
                {
                    ResponseEntity.noContent().build()
                }
                else
                {
                    ResponseEntity.notFound().build()
                }
            }
    }
}