package com.example.book.exception

class BusinessError(message: String, val errorCode: Int) : RuntimeException(message)
