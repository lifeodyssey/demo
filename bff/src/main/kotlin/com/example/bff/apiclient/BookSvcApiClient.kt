package com.example.bff.apiclient

import com.example.bff.dto.Book
import com.example.bff.dto.BookDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "book-svc", url = "\${book.svc.url}")
interface BookSvcApiClient {
    @PostMapping
    fun createBook(@RequestBody book: BookDto): ResponseEntity<String>

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<Book>

    @GetMapping("")
    fun findAllBooks(): ResponseEntity<List<Book>>

    @PutMapping("/{bookId}")
    fun updateBookById(@PathVariable bookId: String, @RequestBody book: BookDto): ResponseEntity<Book>

    @DeleteMapping("/{bookId}")
    fun deleteBookById(@PathVariable bookId: String): ResponseEntity<Any>

    @DeleteMapping
    fun deleteAllBooks(): ResponseEntity<Unit>

}