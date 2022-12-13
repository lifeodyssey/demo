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
import java.util.*

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    fun createBook(book: BookDto): Book {
        val bookToSave = book.toBook()
        return bookRepository.save(bookToSave)
    }

    fun findBookByID(bookID: String): Optional<Book> {
        return bookRepository.findById(bookID)
    }

    fun findAllBooks(): List<Book> {
        return bookRepository.findAll()
    }
    fun updateBookByID(bookId: String, book: BookDto) {
        val bookToBeUpdated = bookRepository.findById(bookId)
        if (bookToBeUpdated.isPresent) {
            val updatedBook = bookToBeUpdated.get().copy(
                bookID=bookId,
                title = book.title,
                authors = book.authors.map { it.toAuthor() },
                rates = book.rates.toRate(),
                abstract = book.abstract,
                details = book.details.toDetail()
            )
            bookRepository.deleteById(bookId)
            bookRepository.save(updatedBook)
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

//TODO 1.using _id as book id instead of new id
//TODO 2.write more response body
//todo 3. exception handling??
//todo 4. add test