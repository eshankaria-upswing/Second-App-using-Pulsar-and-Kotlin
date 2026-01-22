package com.example.spring_reactor2.controller

import com.example.spring_reactor2.model.BookRequest
import com.example.spring_reactor2.repository.BookRequestRepository
import com.example.spring_reactor2.service.BookRequestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/bookRequest")
class BookRequestController (private val bookRequestService: BookRequestService) {

    @GetMapping()
    fun getAllBookRequests(): Flux<BookRequest> {
        return bookRequestService.getAllBookRequests()
    }
}