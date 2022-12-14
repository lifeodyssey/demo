package com.example.demo.integration.controller.bookController

import com.example.demo.controller.BookController
import com.example.demo.mapper.toAuthor
import com.example.demo.mapper.toDetail
import com.example.demo.mapper.toRate
import com.example.demo.service.BookService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import io.mockk.verify
import models.dto.AuthorDto
import models.dto.BookDto
import models.dto.DetailDto
import models.dto.RatesDto
import models.entity.Book
import org.bson.types.ObjectId
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(BookController::class)
@AutoConfigureJsonTesters
class BookControllerTest {
    @MockkBean
    lateinit var bookService: BookService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var bookJson: JacksonTester<BookDto>
    private val bookId = ObjectId().toString()
    private val title = "System Design Interview ??? An insider's guide"
    private val author = AuthorDto("Alex Xu")
    private val rates = RatesDto()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider???s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat???s inside?\n- An insider???s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailDto(isbn = "979-8664653403")
    private var bookDto: BookDto = BookDto(
        title = title, authors = listOf(author), rates = rates, abstract = abstract, details = detail
    )
    private val savedBook = Book(
        bookId = bookId,
        title = title,
        authors = listOf(author.toAuthor()),
        rates = rates.toRate(),
        abstract = abstract,
        details = detail.toDetail()
    )

    @Test
    fun `createBook should return book Id if created`() {
        // Given
        val createBookRequest: MockHttpServletRequestBuilder =
            post("/books").contentType(MediaType.APPLICATION_JSON).content(bookJson.write(bookDto).json)
        bookId
        every { bookService.createBook(bookDto) }.returns(savedBook)
        // When
        mockMvc.perform(createBookRequest)
            // Then
            .andExpect(status().isCreated).andExpect(jsonPath("$").value(bookId))
        verify { bookService.createBook(bookDto) }
    }

    @Test
    fun `findBookById should return book if found`() {
        // Given
        every { bookService.findBookById(bookId) }.returns(savedBook)
        // When
        mockMvc.perform(get("/books/$bookId"))
            // Then
            .andExpect(status().isOk).andExpect(jsonPath("$.bookId").value(bookId))
            .andExpect(jsonPath("$.title").value(title)).andExpect(jsonPath("$.authors", hasSize<Int>(1)))
            .andExpect(jsonPath("$.authors[0].authorName").value(author.authorName))
            .andExpect(jsonPath("$.rates.rate").value(rates.rate))
            .andExpect(jsonPath("$.rates.rateAmount").value(rates.rateAmount))
            .andExpect(jsonPath("$.abstract").value(abstract)).andExpect(jsonPath("$.details.isbn").value(detail.isbn))

        verify { bookService.findBookById(bookId) }
    }

    @Test
    fun `findAll should return all books`() {
        // Given
        every { bookService.findAllBooks() }.returns(listOf(savedBook))
        // When
        mockMvc.perform(get("/books"))
            // Then
            .andExpect(status().isOk).andExpect(jsonPath("$", hasSize<Int>(1)))
            .andExpect(jsonPath("$[0].bookId").value(bookId)).andExpect(jsonPath("$[0].title").value(title))
            .andExpect(jsonPath("$[0].authors", hasSize<Int>(1)))
            .andExpect(jsonPath("$[0].authors[0].authorName").value(author.authorName))
            .andExpect(jsonPath("$[0].rates.rate").value(rates.rate))
            .andExpect(jsonPath("$[0].rates.rateAmount").value(rates.rateAmount))
            .andExpect(jsonPath("$[0].abstract").value(abstract))
            .andExpect(jsonPath("$[0].details.isbn").value(detail.isbn))

        verify { bookService.findAllBooks() }
    }

    @Test
    fun `updateBookById should return updated book`() {
        // Given
        val updatedBookDto = bookDto.copy(title = "system design volume 2")
        val updatedBook = savedBook.copy(title = "system design volume 2")
        val updateBookRequest: MockHttpServletRequestBuilder =
            put("/books/$bookId").contentType(MediaType.APPLICATION_JSON).content(bookJson.write(updatedBookDto).json)
        every { bookService.updateBookById(bookId, updatedBookDto) }.returns(updatedBook)

        // When
        mockMvc.perform(updateBookRequest)
            // Then
            .andExpect(status().isOk).andExpect(jsonPath("$.bookId").value(bookId))
            .andExpect(jsonPath("$.title").value(updatedBook.title))
            .andExpect(jsonPath("$.authors", hasSize<Int>(1)))
            .andExpect(jsonPath("$.authors[0].authorName").value(author.authorName))
            .andExpect(jsonPath("$.rates.rate").value(rates.rate))
            .andExpect(jsonPath("$.rates.rateAmount").value(rates.rateAmount))
            .andExpect(jsonPath("$.abstract").value(abstract))
            .andExpect(jsonPath("$.details.isbn").value(detail.isbn))

        verify { bookService.updateBookById(bookId, updatedBookDto) }
    }

    @Test
    fun `deleteBookById should return no content`() {

        // Given

        justRun { bookService.deleteBookById(bookId) }
        // When
        mockMvc.perform(delete("/books/$bookId"))
            // Then
            .andExpect(status().isNoContent)
        verify { bookService.deleteBookById(bookId) }
    }

    @Test
    fun `deleteAllBooks should return no content`() {

        // Given

        justRun { bookService.deleteAllBooks() }
        // When
        mockMvc.perform(delete("/books"))
            // Then
            .andExpect(status().isNoContent)
        verify { bookService.deleteAllBooks() }
    }
}
