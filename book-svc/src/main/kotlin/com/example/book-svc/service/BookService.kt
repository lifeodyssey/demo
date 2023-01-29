package com.example.`book-svc`.service

import com.example.`book-svc`.exception.BusinessError
import com.example.`book-svc`.mapper.toAuthor
import com.example.`book-svc`.mapper.toBook
import com.example.`book-svc`.mapper.toDetail
import com.example.`book-svc`.mapper.toRate
import com.example.`book-svc`.repository.BookRepository
import models.dto.BookDto
import models.entity.Book
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    private val logger = LogManager.getLogger(BookService::class.java)
    fun createBook(book: BookDto): Book {
        logger.debug("Received request to create book:{}", book)
        val bookToSave = book.toBook()
        val savedBook = bookRepository.save(bookToSave)
        logger.debug("Received request to create book:{}", savedBook.bookId)
        return savedBook
    }

    fun findBookById(bookId: String): Book {
        logger.debug("Received request to find book by id:{}", bookId)
        try {

            return bookRepository.findById(bookId).orElseThrow {
                BusinessError("Book with id $bookId not found", 404)
            }
        } catch (e: BusinessError) {
            logger.error("Error occurred while finding book by id:{}", bookId, e)
            throw e
        }
    }

    fun findAllBooks(): List<Book> {
        logger.debug("Received request to find all books ")
        return bookRepository.findAll()
    }

    fun updateBookById(bookId: String, book: BookDto): Book {
        logger.debug("Received request to update book by id:{}", bookId)
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
            logger.error("Error occurred while updating book by id:{}", bookId, e)
            throw e
        }
    }

    fun deleteBookById(bookId: String) {
        logger.debug("Received request to delete book by id:{}", bookId)
        try {
            bookRepository.findById(bookId).orElseThrow {
                BusinessError("Book with id $bookId not found", 404)
            }
            bookRepository.deleteById(bookId)
        } catch (e: BusinessError) {
            logger.error("Error occurred while delete book by id:{}", bookId, e)
            throw e
        }
    }

    fun deleteAllBooks() {
        logger.debug("Received request to delete all books")
        bookRepository.deleteAll()
    }
}
