package com.example.library.messaging.config

import org.apache.pulsar.client.api.PulsarClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PulsarConfig(
    @Value("\${spring.pulsar.client.service-url}")
    private val serviceUrl: String
) {

    @Bean
    fun pulsarClient(): PulsarClient {
        return PulsarClient.builder()
            .serviceUrl(serviceUrl)
            .build()
    }
}
