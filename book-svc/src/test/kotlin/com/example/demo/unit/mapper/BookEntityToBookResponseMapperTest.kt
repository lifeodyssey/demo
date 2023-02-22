package com.example.demo.unit.mapper

import com.example.demo.mapper.toBookResponse
import models.entity.Author
import models.entity.Book
import models.entity.BookItem
import models.entity.Detail
import models.entity.Rates
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

class BookEntityToBookResponseMapperTest {
    @Test
    fun `BookEntity to BookResponse mapper test`() {
        // Given
        val bookEntity = Book(
            bookId = "1",
            title = "The Great Gatsby",
            authors = listOf(Author(authorName = "F. Scott")),
            rates = Rates(rate = BigDecimal.valueOf(4.0), rateAmount = 10),
            abstract = "A novel about the corruption of the American Dream.",
            bookItems = listOf(
                BookItem(
                    currency = "USD",
                    category = "Novel",
                    price = BigDecimal.valueOf(10),
                    type = "Paper",
                    location = GeoJsonPoint(50.0, 50.0)
                )
            ),
            details = Detail(isbn = "979-10-59-0591-02")
        )

        // When
        val bookResponse = bookEntity.toBookResponse()

        // Then
        assertThat(bookResponse.bookId).isEqualTo("1")
        assertThat(bookResponse.title).isEqualTo("The Great Gatsby")
        assertThat(bookResponse.authors.size).isEqualTo(1)
        assertThat(bookResponse.authors[0].authorName).isEqualTo("F. Scott")
        assertThat(bookResponse.rates.rate).isEqualTo(BigDecimal.valueOf(4.0))
        assertThat(bookResponse.rates.rateAmount).isEqualTo(10)
        assertThat(bookResponse.abstract).isEqualTo("A novel about the corruption of the American Dream.")
        assertThat(bookResponse.bookItems!!.size).isEqualTo(1)
        assertThat(bookResponse.bookItems!![0].currency).isEqualTo("USD")
        assertThat(bookResponse.bookItems!![0].category).isEqualTo("Novel")
        assertThat(bookResponse.bookItems!![0].price).isEqualTo(BigDecimal.valueOf(10))
        assertThat(bookResponse.bookItems!![0].type).isEqualTo("Paper")
        assertThat(bookResponse.bookItems!![0].location).isEqualTo(GeoJsonPoint(50.0, 50.0))
        assertThat(bookResponse.details.isbn).isEqualTo("979-10-59-0591-02")
    }
}
