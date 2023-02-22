package com.example.demo.system

import com.example.demo.mapper.toBookEntity
import com.example.demo.mapper.toBookResponse
import io.mockk.junit5.MockKExtension
import models.dto.AuthorRequest
import models.dto.BookRequest
import models.dto.BookResponse
import models.dto.DetailRequest
import models.dto.RatesRequest
import models.entity.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@ExtendWith(MockKExtension::class)
class BookE2ETests : DemoApplicationTestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate
    private val title = "System Design Interview – An insider's guide"
    private val author = AuthorRequest("Alex Xu")
    private val rates = RatesRequest()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailRequest(isbn = "979-8664653403")
    private var bookRequest: BookRequest = BookRequest(
        title = title,
        authors = listOf(author),
        rates = rates,
        abstract = abstract,
        details = detail
    )
    private lateinit var savedBook: Book
    private lateinit var bookResponse: BookResponse

    @BeforeEach
    fun beforeEach() {
        mongoTemplate.dropCollection(Book::class.java)
        mongoTemplate.createCollection(Book::class.java)
        savedBook = mongoTemplate.save(bookRequest.toBookEntity())
        bookResponse = savedBook.toBookResponse()
    }

    @Test
    fun `createBook should return book id when book is created`() {
        // Given
        val createBookRequest = HttpEntity<BookRequest>(bookRequest)
        // When
        val createBookResponse = restTemplate.postForEntity("/books", createBookRequest, String::class.java)
        // Then
        assertThat(createBookResponse.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(createBookResponse.body).isNotNull
    }

    @Test
    fun `getBookById should return book when book is found`() {
        // Given
        val bookId = bookResponse.bookId
        // When
        val getBookResponse = restTemplate.getForEntity("/books/$bookId", BookResponse::class.java)
        // Then
        assertThat(getBookResponse.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(getBookResponse.body!!.bookId).isEqualTo(bookResponse.bookId)
        assertThat(getBookResponse.body!!.title).isEqualTo(bookResponse.title)
        assertThat(getBookResponse.body!!.authors.size).isEqualTo(bookResponse.authors.size)
        assertThat(getBookResponse.body!!.authors[0].authorName).isEqualTo(bookResponse.authors[0].authorName)
        assertThat(getBookResponse.body!!.rates.rate).isEqualTo(bookResponse.rates.rate)
        assertThat(getBookResponse.body!!.rates.rateAmount).isEqualTo(bookResponse.rates.rateAmount)
        assertThat(getBookResponse.body!!.abstract).isEqualTo(bookResponse.abstract)
        assertThat(getBookResponse.body!!.details.isbn).isEqualTo(bookResponse.details.isbn)
    }

    @Test
    fun `findAllBook should return all books in the db`() {

        val getBooksResponse = restTemplate.getForEntity(
            "/books",
            List::class.java
        )
        // Then
        assertThat(getBooksResponse.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(getBooksResponse.body!!.size).isEqualTo(1)
//        assertThat(getBooksResponse.body!![0].bookId).isEqualTo(bookResponse.bookId)todo assert content
    }

    @Test
    fun `updateBookById should return updated book`() {
        val updatedBookRequest = bookRequest.copy(title = "system design volume 22")
        val updateBookRequest = HttpEntity<BookRequest>(updatedBookRequest)
        val putBookResponse =
            restTemplate.exchange("/books/${savedBook.bookId}", HttpMethod.PUT, updateBookRequest, Book::class.java)
        // Then
        assertThat(putBookResponse.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(putBookResponse.body!!.bookId).isEqualTo(bookResponse.bookId)
        assertThat(putBookResponse.body!!.title).isEqualTo(updatedBookRequest.title)
        assertThat(putBookResponse.body!!.authors.size).isEqualTo(bookResponse.authors.size)
        assertThat(putBookResponse.body!!.authors[0].authorName).isEqualTo(bookResponse.authors[0].authorName)
    }

    @Test
    fun `deleteBookById should return return no content `() {
        // Given
        // When
        val response = restTemplate.exchange("/books/${savedBook.bookId}", HttpMethod.DELETE, null, Unit::class.java)
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun `deleteAllBook should return return no content `() {
        // Given
        // When
        val response = restTemplate.exchange("/books", HttpMethod.DELETE, null, Unit::class.java)
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}
