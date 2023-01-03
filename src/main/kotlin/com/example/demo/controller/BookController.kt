package com.example.demo.controller

import com.example.demo.exception.BusinessError
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
        logger.info("Successfully created book:{}", bookId)
        return ResponseEntity.status(HttpStatus.CREATED).body(bookId)
    }

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<Any> {
        logger.debug("Received request to find book by id:{}", bookId)
        return try {
            val book = bookService.findBookById(bookId)
            logger.info("Successfully found book by id:{}", bookId)
            ResponseEntity.ok(book)
        } catch (e: BusinessError) {
            logger.error("Error occurred while finding book by id:{}", bookId, e)
            ResponseEntity.status(e.errorCode).body(e.message)
        }
    }

    @GetMapping
    fun findAllBooks(): ResponseEntity<List<Book>> {
        logger.debug("Received request to find all books ")
        val books = bookService.findAllBooks()
        logger.info("Successfully find all  books")
        return ResponseEntity.ok(books)
    }

    @PutMapping("/{bookId}")
    fun updateBookById(@PathVariable bookId: String, @RequestBody book: BookDto): ResponseEntity<Any> {
        logger.debug("Received request to update book by id:{}", bookId)
        return try {
            val updatedBook = bookService.updateBookById(bookId, book)
            logger.info("Successfully update book by id:{}", bookId)
            ResponseEntity.ok(updatedBook)
        } catch (e: BusinessError) {
            logger.error("Error occurred while updating book by id:{}", bookId, e)
            ResponseEntity.status(e.errorCode).body(e.message)
        }
    }

    @DeleteMapping("/{bookId}")
    fun deleteBookById(@PathVariable bookId: String): ResponseEntity<Any> {
        logger.debug("Received request to delete book by id:{}", bookId)
        return try {
            bookService.deleteBookById(bookId)
            logger.info("Successfully delete book by id:{}", bookId)
            ResponseEntity.noContent().build()
        } catch (e: BusinessError) {
            logger.error("Error occurred while delete book by id:{}", bookId, e)
            ResponseEntity.status(e.errorCode).body(e.message)
        }
    }

    @DeleteMapping
    fun deleteAllBooks(): ResponseEntity<Unit> {
        logger.debug("Received request to delete all books")
        bookService.deleteAllBooks()
        logger.debug("Successfully delete all books")
        return ResponseEntity.noContent().build()
    }
}
