package com.example.spring_reactor2.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("book_requests")
data class BookRequest(
    @Id
    val id: Long? = null,
    val bookId: Long,
    val userId: Long
)
