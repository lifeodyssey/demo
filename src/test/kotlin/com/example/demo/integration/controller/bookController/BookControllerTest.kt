package com.example.demo.integration.controller.bookController

import com.example.demo.controller.BookController
import com.example.demo.entity.book.Author
import com.example.demo.entity.book.BookDto
import com.example.demo.entity.book.Detail
import com.example.demo.entity.book.Rates
import com.example.demo.service.BookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(BookController::class)
class BookControllerTest{
    @MockBean
    lateinit var bookService: BookService
    @Autowired
    lateinit var mockMvc:MockMvc

    val bookID:UUID=UUID.randomUUID()
    private val title="System Design Interview – An insider's guide"
    private val author=Author("Alex Xu")
    private val rates=Rates()
    private val abstract="System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail=Detail(isbn = "979-8664653403")

    private val CREATE_REQUEST_BODY="""
            {
                "title": "%s",
                "authors": "%s",
                "rates": "%s",
                "abstract": "%s",
                "details": "%s",
            }
            """

    @Test
    @DisplayName("/book (post)")
    fun should_create_book_item_successfully() {
        //Given
        val createBookRequestJson=String.format(CREATE_REQUEST_BODY,
            title,author,rates,abstract,detail
        )
        val book=            BookDto(
            title = title,
            authors = listOf(author),
            rates = rates,
            abstract = abstract,
            details = detail
        )
        //When
        whenever (bookService.createBook(book)).thenReturn(book)
        val response = mockMvc.perform(
            post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBookRequestJson)
        )
            .andReturn()
            .response

        //Then
        assertEquals(HttpStatus.CREATED.value(),response.status)
        verify(bookService).createBook(book)

    }

}