package com.example.spring_reactor2.repository

import com.example.spring_reactor2.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findByName(name: String): Mono<User>
    fun deleteUserById(Id: Long): Mono<Boolean>
}