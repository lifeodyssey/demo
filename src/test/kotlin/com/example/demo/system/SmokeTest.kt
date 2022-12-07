package com.example.demo.system

import com.example.demo.controller.BookController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SmokeTest: DemoApplicationTestBase() {
    @Autowired
    lateinit var bookController:BookController
    @Test
    fun main() {
        assertThat(bookController).isNotNull
    }
}
