package com.example.spring_reactor2.controller

import com.example.spring_reactor2.model.User
import com.example.spring_reactor2.service.BookService
import com.example.spring_reactor2.service.UserService
import org.springframework.http.HttpStatus
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
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsers() : Flux<User> {
        return userService.getAllUsers()
    }

    @PostMapping
    fun addUser(@RequestBody user: User): Mono<ResponseEntity<User>> {
        return userService.addUser(user.name)
            .map { ResponseEntity.ok(it) }
    }

    @DeleteMapping("{userId}")
    fun deleteUser(@PathVariable userId: Long): Mono<ResponseEntity<Void>> {
        return userService.deleteUser(userId)
        .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): Mono<ResponseEntity<User>> {
        return userService.getUserById(userId)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping("/name/{userName}")
    fun getUserByName(@PathVariable userName: String): Mono<ResponseEntity<User>> {
        return userService.getUserByName(userName)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

}