package com.example.demo.controller

import com.example.demo.service.BookService
import models.dto.BookDto
import models.entity.Book
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
    fun createBook(@RequestBody book: BookDto): ResponseEntity<String> {
        logger.debug("Received request to create book:{}", book)
        val bookId = bookService.createBook(book).bookId
        logger.debug("Successfully created book:{}", bookId)
        return ResponseEntity.status(HttpStatus.CREATED).body(bookId)
    }

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<Book> {
        logger.debug("Received request to find book by id:{}", bookId)
        val book = bookService.findBookById(bookId)
        logger.debug("Successfully found book by id:{}", bookId)
        return ResponseEntity.ok(book)
    }

    @GetMapping
    fun findAllBooks(): ResponseEntity<List<Book>> {
        logger.debug("Received request to find all books ")
        val books = bookService.findAllBooks()
        logger.debug("Successfully find all  books")
        return ResponseEntity.ok(books)
    }

    @PutMapping("/{bookId}")
    fun updateBookById(@PathVariable bookId: String, @RequestBody book: BookDto): ResponseEntity<Book> {
        logger.debug("Received request to update book by id:{}", bookId)
        val updatedBook = bookService.updateBookById(bookId, book)
        logger.debug("Successfully update book by id:{}", bookId)
        return ResponseEntity.ok(updatedBook)
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
