package com.example.book.service

import com.example.book.controller.dto.AuthorRequest
import com.example.book.controller.dto.BookRequest
import com.example.book.controller.dto.DetailRequest
import com.example.book.controller.dto.RatesRequest
import com.example.book.exception.BusinessError
import com.example.book.mapper.toBookEntity
import com.example.book.repository.BookRepository
import com.example.book.repository.entity.Book
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
internal class BookServiceTest {
    private val title = "System Design Interview – An insider's guide"
    private val author = AuthorRequest("Alex Xu")
    private val rates = RatesRequest()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailRequest(isbn = "979-8664653403")

    @MockK
    lateinit var bookRepository: BookRepository

    @InjectMockKs
    lateinit var bookService: BookService

    private lateinit var savedBookId: String
    private lateinit var savedBook: Book
    private lateinit var bookRequest: BookRequest

    @BeforeEach
    fun prepareDatInDB() {
        bookRequest = BookRequest(
            title = title, authors = listOf(author), rates = rates, abstract = abstract, details = detail
        )

        savedBook = bookRequest.toBookEntity().copy(
            bookId = ObjectId().toString()
        )
        savedBookId = savedBook.bookId!!
    }

    @Test
    fun `createBook should return book if saved`() {
        // Given
        every { bookRepository.save(any()) }.returns(savedBook)
        // When
        val createBook = bookService.createBook(bookRequest)
        // Then
        verify { bookRepository.save(any()) }
        assertEquals(createBook.abstract, savedBook.abstract)
        assertEquals(createBook.abstract, savedBook.abstract)
        assertEquals(createBook.authors.size, savedBook.authors.size)
    }

    @Test
    fun `findBookById should return book if found`() {
        every { bookRepository.findById(savedBookId) }.returns(Optional.of(savedBook))
        val foundedBook = bookService.findBookById(savedBookId)

        verify { bookRepository.findById(savedBookId) }
        assertThat(foundedBook).isNotNull
        assertEquals(savedBook.bookId, foundedBook.bookId)
    }

    @Test
    fun `findBookById should throw ApiError if book not found`() {
        // Given
        every { bookRepository.findById(any()) } returns Optional.empty()
        // When Then
        assertThrows<BusinessError> {
            bookService.findBookById("123")
        }
    }

    @Test
    fun `findAllBooks should return all the books in the db`() {
        every { bookRepository.findAll() }.returns(listOf(savedBook))
        val bookList = bookService.findAllBooks()

        verify { bookRepository.findAll() }
        assertTrue(bookList.isNotEmpty())
        assertEquals(1, bookList.size)
        assertEquals(savedBook.bookId, bookList[0].bookId)
    }

    @Test
    fun `updateBookById should return book if updated`() {
        val updatedBookRequest = bookRequest.copy(title = "system design volume 22")
        val updateBook = savedBook.copy(title = "system design volume 22")
        every { bookRepository.findById(savedBookId) }.returns(Optional.of(savedBook))
        every { bookRepository.save(any()) }.returns(updateBook)
        val updateResult = bookService.updateBookById(savedBookId, updatedBookRequest)

        verify { bookRepository.save(any()) }
        assertThat(updateBook).isNotNull
        assertEquals(updateBook.bookId, updateResult.bookId)
        assertEquals(updateBook.title, updateResult.title)
    }

    @Test
    fun `updateBookById should throw ApiError if book not found`() {
        // Given
        every { bookRepository.findById(any()) } returns Optional.empty()
        // When Then
        assertThrows<BusinessError> {
            bookService.updateBookById("123", bookRequest)
        }
    }

    @Test
    fun `deleteBookById should return nothing if founded`() {
        every { bookRepository.findById(savedBookId) }.returns(Optional.of(savedBook))
        justRun { bookRepository.deleteById(savedBookId) }
        bookService.deleteBookById(savedBookId)
        verify { bookRepository.deleteById(savedBookId) }
    }

    @Test
    fun `deleteBookById should throw ApiError if book not found`() {
        // Given
        every { bookRepository.findById(any()) } returns Optional.empty()
        // When Then
        assertThrows<BusinessError> {
            bookService.deleteBookById("123")
        }
    }

    @Test
    fun `deleteAllBooks should delete all the books in db`() {
        // Given
        justRun { bookRepository.deleteAll() }
        every { bookRepository.findAll() } returns emptyList()
        // When
        bookRepository.deleteAll()
        // Then
        assertEquals(0, bookRepository.findAll().size)
        verify(exactly = 1) { bookRepository.deleteAll() }
    }
}
