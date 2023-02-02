package com.example.bff.service

import com.example.bff.apiclient.BookSvcApiClient
import com.example.bff.dto.Book
import com.example.bff.dto.BookDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class BookBffService(private val bookSvcApiClient: BookSvcApiClient) {

    fun createBook(book: BookDto): ResponseEntity<String> {
        return bookSvcApiClient.createBook(book)
    }

    fun findBookById(bookId: String): ResponseEntity<Book> {
        return bookSvcApiClient.findBookById(bookId)
    }

    fun findAllBooks(): ResponseEntity<List<Book>> {
        return bookSvcApiClient.findAllBooks()
    }

    fun updateBookById(bookId: String, book: BookDto): ResponseEntity<Book> {
        return bookSvcApiClient.updateBookById(bookId, book)
    }

    fun deleteBookById(bookId: String): ResponseEntity<Any> {
        return bookSvcApiClient.deleteBookById(bookId)
    }

    fun deleteAllBooks(): ResponseEntity<Unit> {
        return bookSvcApiClient.deleteAllBooks()
    }
}
