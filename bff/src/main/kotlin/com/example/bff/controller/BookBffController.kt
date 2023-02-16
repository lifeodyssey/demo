package com.example.bff.controller

import com.example.bff.dto.Book
import com.example.bff.dto.BookDto
import com.example.bff.service.BookBffService
import org.apache.logging.log4j.LogManager
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
class BookBffController(private val bookBffService: BookBffService) {

    private val logger = LogManager.getLogger(BookBffController::class.java)

    @PostMapping
    fun createBook(@RequestBody book: BookDto): ResponseEntity<String> {
        logger.debug("Received request to create book:{}", book)
        val bookId = bookBffService.createBook(book)
        logger.debug("Successfully created book:{}", bookId)
        return bookId
    }

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<Book> {
        logger.debug("Received request to find book by id:{}", bookId)
        val book = bookBffService.findBookById(bookId)
        logger.debug("Successfully found book by id:{}", bookId)
        return book
    }

    @GetMapping
    fun findAllBooks(): ResponseEntity<List<Book>> {
        logger.debug("Received request to find all books ")
        val books = bookBffService.findAllBooks()
        logger.debug("Successfully find all  books")
        return books
    }

    @PutMapping("/{bookId}")
    fun updateBookById(@PathVariable bookId: String, @RequestBody book: BookDto): ResponseEntity<Book> {
        logger.debug("Received request to update book by id:{}", bookId)
        val updatedBook = bookBffService.updateBookById(bookId, book)
        logger.debug("Successfully update book by id:{}", bookId)
        return updatedBook
    }

    @DeleteMapping("/{bookId}")
    fun deleteBookById(@PathVariable bookId: String): ResponseEntity<Any> {
        logger.debug("Received request to delete book by id:{}", bookId)
        bookBffService.deleteBookById(bookId)
        logger.debug("Successfully delete book by id:{}", bookId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteAllBooks(): ResponseEntity<Any> {
        logger.debug("Received request to delete all books")
        bookBffService.deleteAllBooks()
        logger.debug("Successfully delete all books")
        return ResponseEntity.noContent().build()
    }
}
