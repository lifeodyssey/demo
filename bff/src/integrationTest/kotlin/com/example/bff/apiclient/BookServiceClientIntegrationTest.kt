import com.example.bff.apiclient.BookSvcApiClient
import com.example.bff.controller.request.AuthorRequest
import com.example.bff.controller.request.BookItemRequest
import com.example.bff.controller.request.BookRequest
import com.example.bff.controller.request.DetailRequest
import com.example.bff.controller.request.RatesRequest
import java.math.BigDecimal
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertEquals

@SpringBootTest(
    webEnvironment = RANDOM_PORT)
class BookServiceClientIntegrationTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var bookSvcApiClient: BookSvcApiClient

    @Test
    fun `test should create book success with valid request`() {
        val bookCreationRequest = BookRequest(
            title = "System Design Interview – An insider's guide",
            authors = listOf(AuthorRequest(authorName = "Alex Xu")),
            rates = RatesRequest(rate = BigDecimal("4.6"), rateAmount = 2144),
            abstract = "System Design Interview - An Insider's Guide (Volume 1) provides a reliable strategy for tackling system design interview questions...",
            bookItems = listOf(
                BookItemRequest(
                    currency = "USD",
                    price = BigDecimal("24.99"),
                    category = "Kindle",
                    type = "E-Book",
                    location = null // Assuming location data isn't provided or needed for the test.
                ),
                BookItemRequest(
                    currency = "USD",
                    price = BigDecimal("28.49"),
                    category = "Paperback",
                    type = "Used",
                    location = null
                ),
                BookItemRequest(
                    currency = "USD",
                    price = BigDecimal("35.99"),
                    category = "Paperback",
                    type = "New",
                    location = null
                )
            ),
            details = DetailRequest(
                asin = "B08CMF2CQF",
                isbn = "979-8664653403"
            )

        )

        val result = bookSvcApiClient.createBook(bookCreationRequest)
        assertEquals(result.statusCode, HttpStatus.CREATED)
        assertEquals(result.body!!.bookId, "dd633efd-219b-44b1-b7bf-9c9cdf570a63")

    }
}