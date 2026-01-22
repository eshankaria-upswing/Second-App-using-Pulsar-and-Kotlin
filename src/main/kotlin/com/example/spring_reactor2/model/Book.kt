package com.example.spring_reactor2.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("books")
data class Book(
    @Id
    val id: Long? = null,
    val title: String,
    val author: String
)
