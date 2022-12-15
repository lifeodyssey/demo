package com.example.demo.exception

class ApiError(message: String, private val errorCode: Int) : RuntimeException(message) {

    fun getErrorCodeValue(): Int {
        return errorCode
    }
}
