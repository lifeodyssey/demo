package com.example.book

import com.example.book.controller.BookController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SmokeTest : BookApplicationTestBase() {
    @Autowired
    lateinit var bookController: BookController

    @Test
    fun main() {
        assertThat(bookController).isNotNull
    }
}
