package com.example.library.exception

class BookAlreadyExistsException(
    title: String,
    author: String
) : RuntimeException("Book $title already exists. Author: $author")