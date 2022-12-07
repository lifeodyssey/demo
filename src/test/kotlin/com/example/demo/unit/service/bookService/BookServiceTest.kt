package com.example.demo.unit.service.bookService

import com.example.demo.entity.book.Author
import com.example.demo.entity.book.BookDto
import com.example.demo.entity.book.Detail
import com.example.demo.entity.book.Rates
import com.example.demo.repository.BookRepository
import com.example.demo.service.BookService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever




@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {
    private val title="System Design Interview – An insider's guide"
    private val author= Author("Alex Xu")
    private val rates= Rates()
    private val abstract="System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail= Detail(isbn = "979-8664653403")
    @Mock
    lateinit var bookRepository: BookRepository
    @InjectMocks
    lateinit var bookService: BookService
    @Test
    @DisplayName("#Create and return a new book")
    fun could_create_and_return_book() {
        //Given
        val book= BookDto(
            title = title,
            authors = listOf(author),
            rates = rates,
            abstract = abstract,
            details = detail
        )
        whenever(bookRepository.save(book)).thenReturn(book)
        //When
        val createdBook = bookService.createBook(book)
        //Then
        verify(bookRepository).save(book)
        assertEquals(book.title, createdBook.title)
        assertEquals(book.authors[0].authorName, createdBook.authors[0].authorName)
        assertEquals(book.rates.rate, createdBook.rates.rate)
        assertEquals(book.rates.rateAmount, createdBook.rates.rateAmount)
        assertEquals(book.abstract, createdBook.abstract)
        assertEquals(book.details.isbn, createdBook.details.isbn)
    }
}