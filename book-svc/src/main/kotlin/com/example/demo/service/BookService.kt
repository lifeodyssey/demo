package com.example.demo.service

import com.example.demo.exception.BusinessError
import com.example.demo.mapper.toBookEntity
import com.example.demo.mapper.toBookResponse
import com.example.demo.repository.BookRepository
import lombok.extern.slf4j.Slf4j
import models.dto.BookRequest
import models.dto.BookResponse
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository
    private val logger = LogManager.getLogger(BookService::class.java)
    fun createBook(bookRequest: BookRequest): BookResponse {
        logger.debug("Received request to create book:{}", bookRequest)
        val savedBook = bookRepository.save(bookRequest.toBookEntity())
        logger.debug("Received request to create book:{}", savedBook.bookId)
        return savedBook.toBookResponse()
    }

    fun findBookById(bookId: String): BookResponse {
        logger.debug("Received request to find book by id:{}", bookId)
        return bookRepository.findById(bookId).map { it.toBookResponse() }
            .orElseThrow { BusinessError("Book with id $bookId not found", 404) }
    }

    fun findAllBooks(): List<BookResponse> {
        logger.debug("Received request to find all books ")
        return bookRepository.findAll().map { it.toBookResponse() }
    }

    fun updateBookById(bookId: String, bookRequest: BookRequest): BookResponse {
        logger.debug("Received request to update book by id:{}", bookId)
        val updatedBook = bookRequest.toBookEntity()
        updatedBook.bookId = bookId
        bookRepository.findById(bookId).ifPresentOrElse(
            {
                bookRepository.save(updatedBook)
                logger.debug("Successfully update book by id:{}", bookId)
            },
            {
                throw BusinessError("Book with id $bookId not found", 404)
            }
        )
        return updatedBook.toBookResponse()
    }

    fun deleteBookById(bookId: String) {
        logger.debug("Received request to delete book by id:{}", bookId)
        bookRepository.findById(bookId).ifPresentOrElse(
            {
                bookRepository.deleteById(bookId)
            },
            {
                throw BusinessError("Book with id $bookId not found", 404)
            }
        )
    }

    fun deleteAllBooks() {
        logger.debug("Received request to delete all books")
        bookRepository.deleteAll()
    }
}
