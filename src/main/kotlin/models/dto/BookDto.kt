package models.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank

data class BookDto(
    @NotBlank(message = "Title is required")
    val title: String,
    @NotBlank(message = "Author is required")
    val authors: List<AuthorDto>,
    val rates: RatesDto,
    val abstract: String="",
//    val prices: List<ItemPriceDto>? = null,
    @NotBlank(message = "Details is required")
    val details: DetailDto,
)

data class AuthorDto(
    val authorName: String
)

data class RatesDto(
    val rate: BigDecimal = BigDecimal.ZERO,
    val rateAmount: Int = 0
)

// data class ItemPriceDto(
//    val currency: String,
//    val price: BigDecimal,
//    val category: String,
//    val type: String?
// )


data class DetailDto(
    val asin: String? = null,
    @NotBlank(message = "ISBN is required")
    val isbn: String,
)
