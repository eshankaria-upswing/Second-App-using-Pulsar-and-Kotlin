package com.example.spring_reactor2.messaging.producer

import com.example.spring_reactor2.event.BookBackInStockEvent
import com.example.spring_reactor2.messaging.config.PulsarTopics
import com.example.spring_reactor2.messaging.consumer.BookBackInStockConsumer
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import kotlin.jvm.java

@Component
class BookEventPublisher (
    private val pulsarClient: PulsarClient
) {

    private val log = LoggerFactory.getLogger(BookEventPublisher::class.java)

    private val producer = pulsarClient
        .newProducer(Schema.JSON(BookBackInStockEvent::class.java))
        .topic(PulsarTopics.BOOK_BACK_IN_STOCK)
        .create()

    fun publishBookBackInStock(bookId: Long): Mono<Void> {
        val event = BookBackInStockEvent(bookId = bookId)

        log.info("BookBackInStockEvent created $event")
        return Mono.fromFuture {
            producer.sendAsync(event)
        }
            .doOnSubscribe {
                log.info("Subscribing to Pulsar publish for bookId={}", bookId)
            }
            .doOnSuccess {
                log.info("Published BookBackInStockEvent for bookId={}", bookId)
            }
            .doOnError { ex ->
                log.error("Failed to publish BookBackInStockEvent for bookId=$bookId", ex)
            }
            .then()
    }
}