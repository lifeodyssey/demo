package com.example.bff.service

import com.example.bff.apiclient.BookSvcApiClient
import com.example.bff.controller.request.BookRequest
import com.example.bff.controller.response.BookCreationResponse
import com.example.bff.controller.response.BookResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BookBffService(private val bookSvcApiClient: BookSvcApiClient) {

    fun createBook(book: BookRequest): ResponseEntity<BookCreationResponse> {
        return bookSvcApiClient.createBook(book)
    }

    fun findBookById(bookId: String): ResponseEntity<BookResponse> {
        return bookSvcApiClient.findBookById(bookId)
    }

    fun findAllBooks(): ResponseEntity<List<BookResponse>> {
        return bookSvcApiClient.findAllBooks()
    }

    fun updateBookById(bookId: String, book: BookRequest): ResponseEntity<BookResponse> {
        return bookSvcApiClient.updateBookById(bookId, book)
    }

    fun deleteBookById(bookId: String): ResponseEntity<Any> {
        return bookSvcApiClient.deleteBookById(bookId)
    }

    fun deleteAllBooks(): ResponseEntity<Any> {
        return bookSvcApiClient.deleteAllBooks()
    }
}
