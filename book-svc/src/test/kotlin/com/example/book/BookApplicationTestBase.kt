package com.example.book

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApplicationTestBase {
    @Test
    fun contextLoads() {
    }
}
