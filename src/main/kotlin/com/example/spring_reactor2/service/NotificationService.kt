package com.example.spring_reactor2.service

import com.example.spring_reactor2.repository.BookRequestRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class NotificationService(
    private val bookRequestRepository: BookRequestRepository
) {

    private val log = LoggerFactory.getLogger(NotificationService::class.java)

    /**
     * Notify all users waiting for a given book.
     */
    fun notifyUsersWaitingForBook(bookId: Long): Mono<Void> {
        return bookRequestRepository.findAllByBookId(bookId)
            .flatMap { request ->
                sendNotification(request.userId, bookId)
            }
            .then()
    }

    /**
     * Placeholder for real notification logic.
     * Email / push / websocket later.
     */
    private fun sendNotification(userId: Long, bookId: Long): Mono<Void> {
        return Mono.fromRunnable {
            log.info("Notifying user {} that book {} is back in stock", userId, bookId)
        }
    }
}
