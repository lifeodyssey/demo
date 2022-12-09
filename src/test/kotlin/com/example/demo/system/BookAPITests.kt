package com.example.demo.system

import models.dto.AuthorDto
import models.dto.BookDto
import models.dto.DetailDto
import models.dto.RatesDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import java.util.UUID

class BookAPITests : DemoApplicationTestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private val title = "System Design Interview – An insider's guide"
    private val author = AuthorDto("Alex Xu")
    private val rates = RatesDto()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailDto(isbn = "979-8664653403")

    @Test
    fun should_create_new_book_and_return_response() {
        // Given
        val bookDto = BookDto(
            title = title,
            authors = listOf(author),
            rates = rates,
            abstract = abstract,
            details = detail
        )
        val createBookRequest = HttpEntity<BookDto>(bookDto)
        // When
        val postBookEntity = restTemplate.postForObject("/book", createBookRequest, UUID::class.java)
        // Then
        assertThat(postBookEntity).isNotNull
    }
}
