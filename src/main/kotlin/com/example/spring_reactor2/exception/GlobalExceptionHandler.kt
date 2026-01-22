package com.example.spring_reactor2.exception

import com.example.library.exception.BookAlreadyExistsException
import com.example.library.exception.BookDoesNotExistException
import com.example.library.exception.BookRequestExists
import com.example.library.exception.OutOfStockException
import com.example.library.exception.UserAlreadyExistsException
import com.example.library.exception.UserDoesNotExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<*> {
//        return ResponseEntity.badRequest().body(ex.message)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.message)
    }

    @ExceptionHandler(BookAlreadyExistsException::class)
    fun handleBookAlreadyExistsException(ex: BookAlreadyExistsException): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.message)
    }

    @ExceptionHandler(BookDoesNotExistException::class)
    fun handleBookDoesNotExistException(ex: BookDoesNotExistException): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.message)
    }

    @ExceptionHandler(OutOfStockException::class)
    fun handleOutOfStockException(ex: OutOfStockException): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)
    }

    @ExceptionHandler(UserDoesNotExistException::class)
    fun handleUserDoesNotExistException(ex: UserDoesNotExistException): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.message)
    }

    @ExceptionHandler(BookRequestExists::class)
    fun handleBookRequestExistsException(ex: BookRequestExists): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)
    }
}