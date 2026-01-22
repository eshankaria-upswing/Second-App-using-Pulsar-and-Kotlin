package com.example.library.exception

class BookDoesNotExistException(
    bookId: Long
) : RuntimeException("The book $bookId doesn't exist.")