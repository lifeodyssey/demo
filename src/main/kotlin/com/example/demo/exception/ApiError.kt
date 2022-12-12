package com.example.demo.exception

import org.springframework.http.HttpStatus

data class ApiError(val status: HttpStatus, val message: String, val errors: List<String>)