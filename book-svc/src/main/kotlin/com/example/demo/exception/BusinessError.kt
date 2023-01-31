package com.example.demo.exception

class BusinessError(message: String, val errorCode: Int) : RuntimeException(message)
