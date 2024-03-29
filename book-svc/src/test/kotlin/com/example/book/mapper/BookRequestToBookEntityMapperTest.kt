package com.example.book.mapper

import com.example.book.controller.dto.AuthorRequest
import com.example.book.controller.dto.BookItemRequest
import com.example.book.controller.dto.BookRequest
import com.example.book.controller.dto.DetailRequest
import com.example.book.controller.dto.RatesRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

class BookRequestToBookEntityMapperTest {

    @Test
    fun `BookRequest to BookEntity mapper test`() {
        // Given
        val bookRequest = BookRequest(
            title = "The Great Gatsby",
            authors = listOf(AuthorRequest(authorName = "F. Scott")),
            rates = RatesRequest(rate = BigDecimal.valueOf(4.0), rateAmount = 10),
            abstract = "A novel about the corruption of the American Dream.",
            bookItems = listOf(
                BookItemRequest(
                    currency = "USD",
                    category = "Novel",
                    price = BigDecimal.valueOf(10),
                    type = "Paper",
                    location = GeoJsonPoint(50.0, 50.0)
                )
            ),
            details = DetailRequest(isbn = "979-10-59-0591-02")
        )

        // When
        val book = bookRequest.toBookEntity()

        // Then
        Assertions.assertThat(book.bookId).isNull()
        Assertions.assertThat(book.title).isEqualTo("The Great Gatsby")
        Assertions.assertThat(book.authors.size).isEqualTo(1)
        Assertions.assertThat(book.authors[0].authorName).isEqualTo("F. Scott")
        Assertions.assertThat(book.rates.rate).isEqualTo(BigDecimal.valueOf(4.0))
        Assertions.assertThat(book.rates.rateAmount).isEqualTo(10)
        Assertions.assertThat(book.abstract).isEqualTo("A novel about the corruption of the American Dream.")
        Assertions.assertThat(book.bookItems!!.size).isEqualTo(1)
        Assertions.assertThat(book.bookItems!![0].currency).isEqualTo("USD")
        Assertions.assertThat(book.bookItems!![0].category).isEqualTo("Novel")
        Assertions.assertThat(book.bookItems!![0].price).isEqualTo(BigDecimal.valueOf(10))
        Assertions.assertThat(book.bookItems!![0].type).isEqualTo("Paper")
        Assertions.assertThat(book.bookItems!![0].location).isEqualTo(GeoJsonPoint(50.0, 50.0))
        Assertions.assertThat(book.details.isbn).isEqualTo("979-10-59-0591-02")
    }
}
