package com.example.library.exception

import com.example.spring_reactor2.model.Book

class OutOfStockException(
    bookId: Long,
    userId: Long,
) : RuntimeException("Book $bookId is out of stock. User $userId has been added to the waitlist")