package com.example.demo.service

import com.example.demo.mapper.toBook
import com.example.demo.repository.BookRepository
import models.dto.BookDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    fun createBook(book: BookDto): UUID {
        val bookToSave = book.toBook()
        val savedBook = bookRepository.save(bookToSave)
        return savedBook.bookID
    }
}
