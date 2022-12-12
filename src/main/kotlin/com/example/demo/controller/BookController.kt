package com.example.demo.controller

import com.example.demo.service.BookService
import models.dto.BookDto
import models.entity.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book).bookID)
    }

    @GetMapping("/{bookID}")
    fun findBookByID(@PathVariable bookID: String): ResponseEntity<Book> {
        val book = bookService.findBookByID(bookID)
        return if (book.isPresent) {
            ResponseEntity.ok(book.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
