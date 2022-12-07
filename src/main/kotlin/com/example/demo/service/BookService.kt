package com.example.demo.service

import com.example.demo.entity.book.BookDto
import com.example.demo.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    fun createBook(book: BookDto): BookDto {
        book.bookID= UUID.randomUUID()
        return bookRepository.save(
            book
        )
    }
}
