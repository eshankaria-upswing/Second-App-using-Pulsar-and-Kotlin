package com.example.spring_reactor2.service

import com.example.library.exception.UserAlreadyExistsException
import com.example.spring_reactor2.model.User
import com.example.spring_reactor2.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService (private val userRepository: UserRepository) {

    fun addUser(name: String): Mono<User> {
        return this.getUserByName(name)
            .flatMap<User> {
                Mono.error(UserAlreadyExistsException(name))
            }
            .switchIfEmpty(userRepository.save(User(name = name)));
//        if (userRepository.findByName(name) != null) {}
//        return userRepository.save(user);
    }

    fun deleteUser(id: Long): Mono<Boolean> {
        return this.userRepository.deleteUserById(id)
    }

    fun getUserById(id: Long): Mono<User> {
        return userRepository.findById(id)
    }

    fun getUserByName(name: String): Mono<User> {
        return userRepository.findByName(name);
    }

    fun getAllUsers(): Flux<User> {
        return userRepository.findAll();
    }

}