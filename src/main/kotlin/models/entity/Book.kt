package models.entity

import com.fasterxml.jackson.annotation.JsonProperty
import nonapi.io.github.classgraph.json.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "book")
data class Book(
    @JsonProperty("bookID") @Id var bookID: String,
    val title: String,
    val authors: List<Author>,
    val rates: Rates,
    val abstract: String,
//    val prices: List<ItemPrice>? = null,
    val details: Detail,
//    val reviews: List<Review>? = null,
)
data class Author(
    val authorName: String
)

data class Detail(
    val asin: String? = "",
    val isbn: String,
)

//data class ItemPrice(
//    val currency: String,
//    val price: BigDecimal,
//    val category: String,
//    val type: String?
//)

data class Rates(
    val rate: BigDecimal = BigDecimal.ZERO,
    val rateAmount: Int = 0
)
//data class Review(
//    val reviewerName: String,
//    val reviewContent: String,
//    val helpful: Int,
//    val rate: BigDecimal
//)
