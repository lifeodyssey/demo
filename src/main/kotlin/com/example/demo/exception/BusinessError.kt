package com.example.demo.exception

class BusinessError(message: String, private val errorCode: Int) : RuntimeException(message)
