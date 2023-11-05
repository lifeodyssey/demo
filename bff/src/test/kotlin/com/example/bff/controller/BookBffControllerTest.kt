package com.example.bff.controller

import com.example.bff.controller.request.Author
import com.example.bff.controller.request.Book
import com.example.bff.controller.request.BookItem
import com.example.bff.controller.request.Detail
import com.example.bff.controller.request.Rates
import com.example.bff.controller.response.AuthorDto
import com.example.bff.controller.response.BookDto
import com.example.bff.controller.response.BookItemDto
import com.example.bff.controller.response.DetailDto
import com.example.bff.controller.response.RatesDto
import com.example.bff.service.BookBffService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.bson.types.ObjectId
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
@WebMvcTest(BookBffController::class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
class BookBffControllerTest {
    private val bookId = ObjectId().toString()
    private val title = "System Design Interview – An insider's guide"
    private val author = AuthorDto("Alex Xu")
    private val rates = RatesDto()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailDto(isbn = "979-8664653403")
    private val bookItems = listOf(
        BookItemDto(
            currency = "USD",
            category = "Novel",
            price = BigDecimal.valueOf(10),
            type = "Paper",
            location = GeoJsonPoint(50.0, 50.0)
        )
    )
    private var bookDto: BookDto = BookDto(
        title = title,
        authors = listOf(author),
        rates = rates,
        abstract = abstract,
        details = detail,
        bookItems = bookItems
    )
    private val savedBook = Book(
        bookId = bookId,
        title = title,
        authors = listOf(Author("Alex Xu")),
        rates = Rates(),
        abstract = abstract,
        details = Detail(isbn = "979-8664653403"),
        bookItems = listOf(
            BookItem(
                currency = "USD",
                category = "Novel",
                price = BigDecimal.valueOf(10),
                type = "Paper",
                location = GeoJsonPoint(50.0, 50.0)
            )
        )
    )

    @Autowired
    lateinit var bookJson: JacksonTester<BookDto>

    @MockkBean
    lateinit var bookBffService: BookBffService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should create book successfully`() {
        // given
        val createBookRequest =
            post("/api/books").contentType(MediaType.APPLICATION_JSON).content(bookJson.write(bookDto).json)
        bookId
        val expectedResponse = ResponseEntity(bookId, HttpStatus.CREATED)
        every { bookBffService.createBook(bookDto) }.returns(expectedResponse)
        // when
        mockMvc.perform(createBookRequest)
            // then
            .andExpect(status().isCreated).andExpect(jsonPath("$").value(bookId))
        verify { bookBffService.createBook(bookDto) }
    }

    @Test
    fun `should find book by id successfully`() {
        every { bookBffService.findBookById(bookId) } returns ResponseEntity(savedBook, HttpStatus.OK)
        mockMvc.perform(get("/api/books/$bookId")).andExpect(status().isOk)
            .andExpect(jsonPath("$.bookId").value(bookId)).andExpect(jsonPath("$.title").value(title)).andExpect(
                jsonPath(
                    "$.authors", Matchers.hasSize<Int>(1)
                )
            ).andExpect(jsonPath("$.authors[0].authorName").value(author.authorName))
            .andExpect(jsonPath("$.rates.rate").value(rates.rate))
            .andExpect(jsonPath("$.rates.rateAmount").value(rates.rateAmount)).andExpect(
                jsonPath("$.abstract").value(abstract)
            ).andExpect(jsonPath("$.details.isbn").value(detail.isbn))
        verify { bookBffService.findBookById(bookId) }
    }

    @Test
    fun `should find all books successfully`() {
        every { bookBffService.findAllBooks() } returns ResponseEntity(listOf(savedBook), HttpStatus.OK)
        mockMvc.perform(get("/api/books"))
            // Then
            .andExpect(status().isOk).andExpect(
                jsonPath(
                    "$", Matchers.hasSize<Int>(1)
                )
            ).andExpect(
                jsonPath("$[0].bookId").value(bookId)
            ).andExpect(jsonPath("$[0].title").value(title))
            .andExpect(jsonPath("$[0].authors", Matchers.hasSize<Int>(1)))
            .andExpect(jsonPath("$[0].authors[0].authorName").value(author.authorName))
            .andExpect(jsonPath("$[0].rates.rate").value(rates.rate))
            .andExpect(jsonPath("$[0].rates.rateAmount").value(rates.rateAmount))
            .andExpect(jsonPath("$[0].abstract").value(abstract))
            .andExpect(jsonPath("$[0].details.isbn").value(detail.isbn))

        verify { bookBffService.findAllBooks() }
    }

    @Test
    fun `should update book by id successfully`() {
        val updatedBookDto = bookDto.copy(title = "system design volume 2")
        val updatedBook = savedBook.copy(title = "system design volume 2")
        every { bookBffService.updateBookById(bookId, updatedBookDto) } returns ResponseEntity(
            updatedBook, HttpStatus.OK
        )
        val updateBookRequest = put("/api/books/$bookId").contentType(MediaType.APPLICATION_JSON)
            .content(bookJson.write(updatedBookDto).json)
        mockMvc.perform(updateBookRequest).andExpect(status().isOk).andExpect(jsonPath("$.bookId").value(bookId))
            .andExpect(jsonPath("$.title").value(updatedBook.title))
            .andExpect(jsonPath("$.authors", Matchers.hasSize<Int>(1)))
            .andExpect(jsonPath("$.authors[0].authorName").value(author.authorName))
            .andExpect(jsonPath("$.rates.rate").value(rates.rate))
            .andExpect(jsonPath("$.rates.rateAmount").value(rates.rateAmount))
            .andExpect(jsonPath("$.abstract").value(abstract)).andExpect(jsonPath("$.details.isbn").value(detail.isbn))
        verify { bookBffService.updateBookById(bookId, updatedBookDto) }
    }

    @Test
    fun `should delete book by id successfully`() {
        every { bookBffService.deleteBookById(bookId) } returns ResponseEntity(HttpStatus.NO_CONTENT)
        mockMvc.perform(delete("/api/books/$bookId")).andExpect(status().isNoContent)
        verify { bookBffService.deleteBookById(bookId) }
    }

    @Test
    fun deleteAllBooks() {
        every { bookBffService.deleteAllBooks() } returns ResponseEntity(HttpStatus.NO_CONTENT)
        mockMvc.perform(delete("/api/books")).andExpect(status().isNoContent)
        verify { bookBffService.deleteAllBooks() }
    }
}
