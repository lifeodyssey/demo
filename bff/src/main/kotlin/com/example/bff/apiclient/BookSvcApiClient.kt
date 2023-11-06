package com.example.bff.apiclient

import com.example.bff.controller.request.BookRequest
import com.example.bff.controller.response.BookCreationResponse
import com.example.bff.controller.response.BookResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "book-svc", url = "\${book.svc.url}/books")
interface BookSvcApiClient {
    @PostMapping
    fun createBook(@RequestBody book: BookRequest): ResponseEntity<BookCreationResponse>

    @GetMapping("/{bookId}")
    fun findBookById(@PathVariable bookId: String): ResponseEntity<BookResponse>

    @GetMapping("")
    fun findAllBooks(): ResponseEntity<List<BookResponse>>

    @PutMapping("/{bookId}")
    fun updateBookById(@PathVariable bookId: String, @RequestBody book: BookRequest): ResponseEntity<BookResponse>

    @DeleteMapping("/{bookId}")
    fun deleteBookById(@PathVariable bookId: String): ResponseEntity<Any>

    @DeleteMapping
    fun deleteAllBooks(): ResponseEntity<Any>
}
