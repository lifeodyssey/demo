package com.example.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ApiExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ApiError::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleApiException(e: ApiError): String {
        return "Error: ${e.message}"
    }
}
