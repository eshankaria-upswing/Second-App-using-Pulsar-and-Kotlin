package com.example.spring_reactor2.dto

data class AddBookRequest(
    val title: String,
    val author: String,
    val totalCount: Int
)
