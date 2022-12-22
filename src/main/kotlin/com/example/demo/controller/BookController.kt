package com.example.demo.controller

import com.example.demo.exception.BusinessError
import com.example.demo.service.BookService
import models.dto.BookDto
import models.entity.Book
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

    @PostMapping
    fun createBook(@RequestBody book: BookDto): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book).bookId)
    }

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<Any> {
        return try {
            val book = bookService.findBookById(bookId)
            ResponseEntity.ok(book)
        } catch (e: BusinessError) {
            ResponseEntity.status(e.errorCode).body(e.message)
        }
    }


    @GetMapping
    fun findAllBooks(): ResponseEntity<List<Book>> {
        val books = bookService.findAllBooks()
        return ResponseEntity.ok(books)
    }

    @PutMapping("/{bookId}")
    fun updateBookById(@PathVariable bookId: String, @RequestBody book: BookDto): ResponseEntity<Any> {
        return try {
            val updatedBook = bookService.updateBookById(bookId, book)
            ResponseEntity.ok(updatedBook)
        } catch (e: BusinessError) {
            ResponseEntity.status(e.errorCode).body(e.message)
        }
    }

    @DeleteMapping("/{bookId}")
    fun deleteBookById(@PathVariable bookId: String): ResponseEntity<Any> {
        return try {
            bookService.deleteBookById(bookId)
            ResponseEntity.noContent().build()
        } catch (e: BusinessError) {
            ResponseEntity.status(e.errorCode).body(e.message)
        }
    }

    @DeleteMapping
    fun deleteAllBooks(): ResponseEntity<Unit> {
        bookService.deleteAllBooks()
        return ResponseEntity.noContent().build()
    }
}
