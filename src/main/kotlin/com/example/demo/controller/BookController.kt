package com.example.demo.controller

import com.example.demo.service.BookService
import models.dto.BookDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/book")
class BookController {
    @Autowired
    private lateinit var bookService: BookService
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createBook(@RequestBody book: BookDto): UUID {
        return bookService.createBook(book)
    }
}
