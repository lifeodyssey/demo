// package com.example.bff.controller
//
// import com.example.bff.BffApplicationTestBase
// import com.example.bff.apiclient.BookSvcApiClient
// import com.example.bff.config.KeycloakConfiguration
// import com.example.bff.dto.Author
// import com.example.bff.dto.AuthorDto
// import com.example.bff.dto.Book
// import com.example.bff.dto.BookDto
// import com.example.bff.dto.BookItem
// import com.example.bff.dto.BookItemDto
// import com.example.bff.dto.Detail
// import com.example.bff.dto.DetailDto
// import com.example.bff.dto.Rates
// import com.example.bff.dto.RatesDto
// import com.ninjasquad.springmockk.MockkBean
// import io.mockk.every
// import io.mockk.junit5.MockKExtension
// import io.mockk.verify
// import org.assertj.core.api.Assertions.assertThat
// import org.bson.types.ObjectId
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.extension.ExtendWith
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.web.client.TestRestTemplate
// import org.springframework.data.mongodb.core.geo.GeoJsonPoint
// import org.springframework.http.HttpEntity
// import org.springframework.http.HttpMethod
// import org.springframework.http.HttpStatus
// import org.springframework.http.ResponseEntity
// import org.springframework.security.web.SecurityFilterChain
// import java.math.BigDecimal
//
//
// @ExtendWith(MockKExtension::class)
// @WebAppConfiguration
// class BookBffE2ETest : BffApplicationTestBase() {
//    private val bookId = ObjectId().toString()
//    private val title = "System Design Interview – An insider's guide"
//    private val author = AuthorDto("Alex Xu")
//    private val rates = RatesDto()
//    private val abstract =
//        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
//    private val detail = DetailDto(isbn = "979-8664653403")
//    private val bookItems = listOf(
//        BookItemDto(
//            currency = "USD",
//            category = "Novel",
//            price = BigDecimal.valueOf(10),
//            type = "Paper",
//            location = GeoJsonPoint(50.0, 50.0)
//        )
//    )
//    private var bookDto: BookDto = BookDto(
//        title = title,
//        authors = listOf(author),
//        rates = rates,
//        abstract = abstract,
//        details = detail,
//        bookItems = bookItems
//    )
//    private val savedBook = Book(
//        bookId = bookId,
//        title = title,
//        authors = listOf(Author("Alex Xu")),
//        rates = Rates(),
//        abstract = abstract,
//        details = Detail(isbn = "979-8664653403"),
//        bookItems = listOf(
//            BookItem(
//                currency = "USD",
//                category = "Novel",
//                price = BigDecimal.valueOf(10),
//                type = "Paper",
//                location = GeoJsonPoint(50.0, 50.0)
//            )
//        )
//    )
//
//    @Autowired
//    lateinit var testRestTemplate: TestRestTemplate
//
//    @MockkBean
//    lateinit var bookSvcApiClient: BookSvcApiClient
//
//    @MockkBean
//    lateinit var filterChain: SecurityFilterChain
//
//
//    @MockkBean
//    lateinit var keycloakConfiguration: KeycloakConfiguration
//
//    //    @Autowired
//   lateinit var mockMvc: MockMvc
//   method 1 wiremock todo
//   method 2 spring add on
//    @Test
//    fun `should create book successfully`() {
//        // given
//        val expectedResponse = ResponseEntity(bookId, HttpStatus.CREATED)
//        every { bookSvcApiClient.createBook(bookDto) } returns expectedResponse
//        every { filterChain.filters } answers { listOf() }
//        // when
//
//        val response = testRestTemplate.postForEntity("/api/books", HttpEntity(bookDto), String::class.java)
//
//        // then
//        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
//        assertThat(response.body).isEqualTo(bookId)
//        verify { bookSvcApiClient.createBook(bookDto) }
//    }
//
//    @Test
//    fun `should find book by id successfully`() {
//        every { bookSvcApiClient.findBookById(bookId) } returns ResponseEntity(savedBook, HttpStatus.OK)
//        val response = testRestTemplate.getForEntity("/api/books/$bookId", Book::class.java)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(response.body).isEqualTo(savedBook)
//        verify { bookSvcApiClient.findBookById(bookId) }
//    }
//
//    @Test
//    fun `should find all books successfully`() {
//        every { bookSvcApiClient.findAllBooks() } returns ResponseEntity(listOf(savedBook), HttpStatus.OK)
//        val response = testRestTemplate.getForEntity("/api/books", List::class.java)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
//       assertThat(response.body).isEqualTo(listOf(savedBook))
//        assertThat(response.body!!.size).isEqualTo(1)
//        verify { bookSvcApiClient.findAllBooks() }
//    }
//
//    @Test
//    fun `should update book by id successfully`() {
//        every { bookSvcApiClient.updateBookById(bookId, bookDto) } returns ResponseEntity(savedBook, HttpStatus.OK)
//        val response = testRestTemplate.exchange(
//            "/api/books/$bookId", HttpMethod.PUT,
//            HttpEntity(bookDto), Book::class.java
//        )
//        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(response.body).isEqualTo(savedBook)
//        verify { bookSvcApiClient.updateBookById(bookId, bookDto) }
//    }
//
//    @Test
//    fun `should delete book by id successfully`() {
//        every { bookSvcApiClient.deleteBookById(bookId) } returns ResponseEntity(HttpStatus.NO_CONTENT)
//        val response = testRestTemplate.exchange("/api/books/$bookId", HttpMethod.DELETE, null, Unit::class.java)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
//        verify { bookSvcApiClient.deleteBookById(bookId) }
//    }
//
//    @Test
//    fun deleteAllBooks() {
//        every { bookSvcApiClient.deleteAllBooks() } returns ResponseEntity(HttpStatus.NO_CONTENT)
//        val response = testRestTemplate.exchange("/api/books", HttpMethod.DELETE, null, Unit::class.java)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
//        verify { bookSvcApiClient.deleteAllBooks() }
//    }
// }
