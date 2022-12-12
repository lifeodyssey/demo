package com.example.demo.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.util.stream.*

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleValidateException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val result = exception.bindingResult
        val message = result.fieldErrors.stream()
            .map { err -> err.defaultMessage }
            .collect(Collectors.joining(", ")).toString()

        val response = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.reasonPhrase,
            message
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.reasonPhrase,
            "Invalid format"
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleGeneralException(exception: Exception): ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            exception.message!!
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}