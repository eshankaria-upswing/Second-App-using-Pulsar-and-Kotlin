package com.example.library.exception

class UserDoesNotExistException (
    userId: Long
) : RuntimeException("The user doesn't exist.")