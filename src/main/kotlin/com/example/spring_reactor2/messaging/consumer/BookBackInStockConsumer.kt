package com.example.spring_reactor2.messaging.consumer

import com.example.spring_reactor2.event.BookBackInStockEvent
import com.example.spring_reactor2.messaging.config.PulsarTopics
import com.example.spring_reactor2.service.NotificationService
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import org.apache.pulsar.client.api.SubscriptionType
import org.apache.pulsar.shade.javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class BookBackInStockConsumer(
    pulsarClient: PulsarClient,
    private val notificationService: NotificationService
) {

    private val log = LoggerFactory.getLogger(BookBackInStockConsumer::class.java)

    private val consumer = pulsarClient
        .newConsumer(Schema.JSON(BookBackInStockEvent::class.java))
        .topic(PulsarTopics.BOOK_BACK_IN_STOCK)
        .subscriptionName("book-back-in-stock-subscription")
        .subscriptionType(SubscriptionType.Shared)
        .subscribe()

    @PostConstruct
    fun startConsuming() {
        log.info("BookBackInStockConsumer started")

        Thread {
            while (true) {
                val msg = consumer.receive()

                try {
                    val event = msg.value
                    log.info("Received BookBackInStockEvent for bookId={}", event.bookId)

                    notificationService.notifyUsersWaitingForBook(event.bookId)

                    consumer.acknowledge(msg)
                } catch (ex: Exception) {
                    log.error("Error processing message", ex)
                    consumer.negativeAcknowledge(msg)
                }
            }
        }.start()
    }
}
