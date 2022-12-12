package com.example.demo.unit.service.bookService

import com.example.demo.mapper.toBook
import com.example.demo.repository.BookRepository
import com.example.demo.service.BookService
import models.dto.AuthorDto
import models.dto.BookDto
import models.dto.DetailDto
import models.dto.RatesDto
import models.entity.Book
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {
    private val title = "System Design Interview – An insider's guide"
    private val author = AuthorDto("Alex Xu")
    private val rates = RatesDto()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailDto(isbn = "979-8664653403")

    @Mock
    lateinit var bookRepository: BookRepository

    @InjectMocks
    lateinit var bookService: BookService

    @Captor
    lateinit var bookCaptor: ArgumentCaptor<Book>

    private val bookDto = BookDto(
        title = title,
        authors = listOf(author),
        rates = rates,
        abstract = abstract,
        details = detail
    )
    private val savedBook = bookDto.toBook()

    @Test
    fun `createBook should return book if found`() {
        // Given
        `when`(bookRepository.save(bookCaptor.capture())).thenReturn(savedBook)
        // When
        bookService.createBook(bookDto)
        // Then
        verify(bookRepository).save(bookCaptor.capture()) // todo:verify other output
    }

    @Test
    fun `findBookById should return book if found`() {
        val savedBook = bookDto.toBook()
        whenever(bookRepository.findByBookID(savedBook.bookID)).thenReturn(Optional.of(savedBook))
        val result = bookService.findBookByID(savedBook.bookID)
        assertTrue(result.isPresent)
        assertEquals(savedBook, result.get())
    }

    @Test
    fun `findBookById should return empty optional if book not found`() {
        whenever(bookRepository.findByBookID(any())).thenReturn(Optional.empty())
        val result = bookService.findBookByID(ObjectId().toString())
        assertFalse(result.isPresent)
    }
}
