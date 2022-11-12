package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DemoApplicationKtTest {
    @Test
    fun main() {
        assertThat(main(arrayOf(""))).isNotNull
    }
}
