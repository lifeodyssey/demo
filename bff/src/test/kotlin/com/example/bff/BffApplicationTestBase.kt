package com.example.bff

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BffApplicationTestBase {
    @Test
    fun contextLoads() {
    }
}
