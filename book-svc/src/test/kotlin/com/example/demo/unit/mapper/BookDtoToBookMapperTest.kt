package com.example.demo.unit.mapper

import com.example.demo.mapper.toBookEntity
import models.dto.AuthorRequest
import models.dto.BookItemRequest
import models.dto.BookRequest
import models.dto.DetailRequest
import models.dto.RatesRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

class BookDtoToBookMapperTest {
    @Test
    fun `BookDto to Book mapper test`() {
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
        assertThat(book.bookId).isNull()
        assertThat(book.title).isEqualTo("The Great Gatsby")
        assertThat(book.authors.size).isEqualTo(1)
        assertThat(book.authors[0].authorName).isEqualTo("F. Scott")
        assertThat(book.rates.rate).isEqualTo(BigDecimal.valueOf(4.0))
        assertThat(book.rates.rateAmount).isEqualTo(10)
        assertThat(book.abstract).isEqualTo("A novel about the corruption of the American Dream.")
        assertThat(book.bookItems!!.size).isEqualTo(1)
        assertThat(book.bookItems!![0].currency).isEqualTo("USD")
        assertThat(book.bookItems!![0].category).isEqualTo("Novel")
        assertThat(book.bookItems!![0].price).isEqualTo(BigDecimal.valueOf(10))
        assertThat(book.bookItems!![0].type).isEqualTo("Paper")
        assertThat(book.bookItems!![0].location).isEqualTo(GeoJsonPoint(50.0, 50.0))
        assertThat(book.details.isbn).isEqualTo("979-10-59-0591-02")
    }
}
