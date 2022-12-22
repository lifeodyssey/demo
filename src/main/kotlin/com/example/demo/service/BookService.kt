package com.example.demo.service

import com.example.demo.exception.BusinessError
import com.example.demo.mapper.toAuthor
import com.example.demo.mapper.toBook
import com.example.demo.mapper.toDetail
import com.example.demo.mapper.toRate
import com.example.demo.repository.BookRepository
import models.dto.BookDto
import models.entity.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    fun createBook(book: BookDto): Book {
        val bookToSave = book.toBook()
        return bookRepository.save(bookToSave)
    }

    fun findBookById(bookId: String): Book {
        try {
            return bookRepository.findById(bookId).orElseThrow {
                BusinessError("Book with id $bookId not found", 404)
            }
        } catch (e: BusinessError) {
            throw e
        }
    }

    fun findAllBooks(): List<Book> {
        return bookRepository.findAll()
    }

    fun updateBookById(bookId: String, book: BookDto): Book {
        try {
            val bookToBeUpdated = bookRepository.findById(bookId).orElseThrow {
                BusinessError("Book with id $bookId not found", 404)
            }
            val updatedBook = bookToBeUpdated.copy(
                bookId = bookId,
                title = book.title,
                authors = book.authors.map { it.toAuthor() },
                rates = book.rates.toRate(),
                abstract = book.abstract,
                details = book.details.toDetail()
            )
            return bookRepository.save(updatedBook)
        } catch (e: BusinessError) {
            throw e
        }
    }

    fun deleteBookById(bookId: String) {
        try {
            bookRepository.findById(bookId).orElseThrow {
                BusinessError("Book with id $bookId not found", 404)
            }
            bookRepository.deleteById(bookId)
        } catch (e: BusinessError) {
            throw e
        }
    }

    fun deleteAllBooks() {
        bookRepository.deleteAll()
    }
}
