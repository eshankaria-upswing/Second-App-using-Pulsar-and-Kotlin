package com.example.library.exception

import jdk.jshell.spi.ExecutionControl

class UserAlreadyExistsException(
    userName: String
) : RuntimeException("User $userName already exists")