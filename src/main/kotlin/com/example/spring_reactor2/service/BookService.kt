package com.example.spring_reactor2.service

import com.example.library.exception.BookAlreadyExistsException
import com.example.library.exception.BookDoesNotExistException
import com.example.spring_reactor2.dto.AddBookRequest
import com.example.spring_reactor2.model.Book
import com.example.spring_reactor2.model.Inventory
import com.example.spring_reactor2.repository.BookRepository
import com.example.spring_reactor2.repository.InventoryRepository
import com.example.spring_reactor2.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookService (
    private val bookRepository: BookRepository,
    private val inventoryRepository: InventoryRepository
) {
    fun addBook(bookRequest: AddBookRequest) : Mono<Book>
    {
        return this.findByBooksByTitleAndAuthor(bookRequest.title, bookRequest.author)
            .flatMap<Book> {
                Mono.error(BookAlreadyExistsException(bookRequest.title, bookRequest.author))
            }
            .switchIfEmpty(
                Mono.defer
                {
                    bookRepository.save(
                        Book(
                            title = bookRequest.title,
                            author = bookRequest.author
                        )
                    )
                        .flatMap { savedBook ->
//                            println("Saving inventory for book ${savedBook.id}")
                            inventoryRepository.save(
                                Inventory(
                                    bookId = savedBook.id!!,
                                    availableCount = bookRequest.totalCount
                                )
                            )
                                .thenReturn(savedBook)
                        }
                }
//                savedBook=this.bookRepository.save(Book(title = bookRequest.title, author = bookRequest.author))
//                this.inventoryRepository.save(Inventory(bookId = savedBook.getId(), availableCount = bookRequest.totalCount))
            )
    }

    fun deleteBook(bookId: Long): Mono<Void>{
        return this.findBookById(bookId)
            .flatMap { book ->
                bookRepository.deleteById(bookId)
//                inventoryRepository.deleteByBookId(bookId)
            }
        .switchIfEmpty(Mono.error(BookDoesNotExistException(bookId)))
    }

    fun getAllBooks(): Flux<Book> {
        return bookRepository.findAll();
    }

    fun findBookById(id: Long): Mono<Book> {
        return bookRepository.findById(id);
    }

    fun findByBooksByTitleAndAuthor(title: String, author: String): Mono<Book> {
        return bookRepository.findByTitleAndAuthor(title, author);
    }
}