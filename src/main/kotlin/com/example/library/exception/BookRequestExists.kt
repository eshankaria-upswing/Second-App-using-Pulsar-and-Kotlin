package com.example.library.exception

class BookRequestExists (
    bookId: Long,
    userId: Long
) : RuntimeException("User with id: $userId has already requested book with id: $bookId")