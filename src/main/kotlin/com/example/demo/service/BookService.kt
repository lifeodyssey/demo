package com.example.demo.service

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
        val foundedBook = bookRepository.findById(bookId)
        if (foundedBook.isPresent) {
            return foundedBook.get()
        } else {
            throw Exception("Book with id $bookId not found")
        }
    }

    fun findAllBooks(): List<Book> {
        return bookRepository.findAll()
    }

    fun updateBookById(bookId: String, book: BookDto): Book {
        val bookToBeUpdated = bookRepository.findById(bookId)
        if (bookToBeUpdated.isPresent) {
            val updatedBook = bookToBeUpdated.get().copy(
                bookId = bookId,
                title = book.title,
                authors = book.authors.map { it.toAuthor() },
                rates = book.rates.toRate(),
                abstract = book.abstract,
                details = book.details.toDetail()
            )
            return bookRepository.save(updatedBook)
        } else {
            throw Exception("Book with id $bookId not found")
        }
    }

    fun deleteBookById(bookId: String) {
        val book = bookRepository.findById(bookId)
        if (book.isPresent) {
            bookRepository.deleteById(bookId)
        } else {
            throw Exception("Book with id $bookId not found")
        }
    }

    fun deleteAllBooks() {
        bookRepository.deleteAll()
    }
}
// todo 3. exception handling??
