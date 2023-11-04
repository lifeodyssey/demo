package com.example.book.controller

import com.example.book.controller.dto.BookRequest
import com.example.book.controller.dto.BookResponse
import com.example.book.service.BookService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController {
    @Autowired
    private lateinit var bookService: BookService
    private val logger = LogManager.getLogger(BookController::class.java)

    @PostMapping
    fun createBook(@RequestBody bookRequest: BookRequest): ResponseEntity<String> {
        logger.debug("Received request to create book:{}", bookRequest)
        val bookId = bookService.createBook(bookRequest).bookId
        logger.debug("Successfully created book:{}", bookId)
        return ResponseEntity.status(HttpStatus.CREATED).body(bookId)
    }

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<BookResponse> {
        logger.debug("Received request to find book by id:{}", bookId)
        val bookResponse = bookService.findBookById(bookId)
        logger.debug("Successfully found book by id:{}", bookId)
        return ResponseEntity.ok(bookResponse)
    }

    @GetMapping
    fun findAllBooks(): ResponseEntity<List<BookResponse>> {
        logger.debug("Received request to find all books ")
        val bookResponses = bookService.findAllBooks()
        logger.debug("Successfully find all  books")
        return ResponseEntity.ok(bookResponses)
    }

    @PutMapping("/{bookId}")
    fun updateBookById(
        @PathVariable bookId: String,
        @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookResponse> {
        logger.debug("Received request to update book by id:{}", bookId)
        val updatedBookResponse = bookService.updateBookById(bookId, bookRequest)
        logger.debug("Successfully update book by id:{}", bookId)
        return ResponseEntity.ok(updatedBookResponse)
    }

    @DeleteMapping("/{bookId}")
    fun deleteBookById(@PathVariable bookId: String): ResponseEntity<Any> {
        logger.debug("Received request to delete book by id:{}", bookId)
        bookService.deleteBookById(bookId)
        logger.debug("Successfully delete book by id:{}", bookId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAllBooks(): ResponseEntity<Unit> {
        logger.debug("Received request to delete all books")
        bookService.deleteAllBooks()
        logger.debug("Successfully delete all books")
        return ResponseEntity.noContent().build()
    }
}
