package com.example.book.integration.repository.bookRepository

import com.example.book.repository.BookRepository
import com.example.book.repository.entity.Author
import com.example.book.repository.entity.Book
import com.example.book.repository.entity.Detail
import com.example.book.repository.entity.Rates
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DataMongoTest
@ExtendWith(SpringExtension::class)
class BookRepositoryTest {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate
    private val title = "System Design Interview – An insider's guide"
    private val author = Author("Alex Xu")
    private val rates = Rates()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = Detail(isbn = "979-8664653403")
    private val bookId = ObjectId().toString()

    @BeforeEach
    fun prepareDataForTest() {
        val book = Book(
            bookId = bookId,
            title = title,
            authors = listOf(author),
            rates = rates,
            abstract = abstract,
            details = detail
        )
        mongoTemplate.save(book)
    }

    @Test
    fun `save should return book if created`() {
        // Given
        val book = Book(
            bookId = bookId,
            title = title,
            authors = listOf(author),
            rates = rates,
            abstract = abstract,
            details = detail
        )
        // When
        val savedBook = bookRepository.save(book)
        // Then
        assertEquals(book.bookId, savedBook.bookId)
        assertEquals(book.title, savedBook.title)
        assertEquals(book.authors.size, savedBook.authors.size)
        assertEquals(book.authors[0].authorName, savedBook.authors[0].authorName)
        assertEquals(book.rates.rate, savedBook.rates.rate)
        assertEquals(book.rates.rateAmount, savedBook.rates.rateAmount)
        assertEquals(book.abstract, savedBook.abstract)
        assertEquals(book.details.isbn, savedBook.details.isbn)
    }

    @AfterEach
    fun cleanUpDatabase() {
        bookRepository.deleteAll()
    }

    @Test
    fun `findById should return book if found`() {
        val book = bookRepository.findById(bookId)
        assertTrue(book.isPresent)
        assertEquals(bookId, book.get().bookId)
        assertEquals(title, book.get().title)
        assertEquals(1, book.get().authors.size)
        assertEquals(author.authorName, book.get().authors[0].authorName)
        assertEquals(rates.rate, book.get().rates.rate)
        assertEquals(rates.rateAmount, book.get().rates.rateAmount)
        assertEquals(abstract, book.get().abstract)
        assertEquals(detail.isbn, book.get().details.isbn)
    }

    @Test
    fun `findById should return empty optional if book not found`() {
        val book = bookRepository.findById(ObjectId().toString())
        assertFalse(book.isPresent)
    }

    @Test
    fun `findAll should return all books in the DB`() {
        val bookList = bookRepository.findAll()
        assertTrue(bookList.isNotEmpty())
        assertEquals(1, bookList.size)
        assertEquals(bookId, bookList[0].bookId)
    }

    @Test
    fun `deleteById should delete book in the DB`() {
        bookRepository.deleteById(bookId)
        val book = bookRepository.findById(bookId)
        assertFalse(book.isPresent)
    }

    @Test
    fun `deleteAll should delete all books in the DB`() {
        bookRepository.deleteAll()
        val bookList = bookRepository.findAll()
        assertEquals(0, bookList.size)
    }
}
