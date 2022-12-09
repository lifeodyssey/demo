package models.dto

import java.math.BigDecimal

data class BookDto(
    val title: String,
    val authors: List<AuthorDto>,
    val rates: RatesDto,
    val abstract: String,
//    val prices: List<ItemPriceDto>? = null,
    val details: DetailDto,
//    val reviews: List<ReviewDto>? = null,
)

data class AuthorDto(
    val authorName: String
)

data class RatesDto(
    val rate: BigDecimal = BigDecimal.ZERO,
    val rateAmount: Int = 0
)

//data class ItemPriceDto(
//    val currency: String,
//    val price: BigDecimal,
//    val category: String,
//    val type: String?
//)

//data class ReviewDto(
//    val reviewerName: String,
//    val reviewContent: String,
//    val helpful: Int,
//    val rate: BigDecimal
//)

data class DetailDto(
    val asin: String? = null,
    val isbn: String,
)
