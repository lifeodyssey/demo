package com.example.`book-svc`.exception

class BusinessError(message: String, val errorCode: Int) : RuntimeException(message)
