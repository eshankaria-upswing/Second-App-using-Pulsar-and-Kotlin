package com.example.spring_reactor2.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    val name: String
)
