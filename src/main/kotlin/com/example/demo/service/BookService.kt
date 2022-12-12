package com.example.demo.service

import com.example.demo.mapper.toBook
import com.example.demo.repository.BookRepository
import models.dto.BookDto
import models.entity.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    fun createBook(book: BookDto): Book {
        val bookToSave = book.toBook()
        return bookRepository.save(bookToSave)
    }

    fun findBookByID(bookID: String): Optional<Book> {
        return bookRepository.findByBookID(bookID)
    }
}
