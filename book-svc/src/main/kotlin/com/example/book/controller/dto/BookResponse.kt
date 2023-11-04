package com.example.book.controller.dto

import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

data class BookResponse(
    var bookId: String,
    val title: String,
    val authors: List<AuthorResponse>,
    val rates: RatesResponse,
    val abstract: String = "",
    val bookItems: List<BookItemResponse>? = null,
    val details: DetailResponse,
)

data class AuthorResponse(
    val authorName: String
)

data class RatesResponse(
    val rate: BigDecimal = BigDecimal.ZERO,
    val rateAmount: Int = 0
)

data class BookItemResponse(
    val currency: String,
    val price: BigDecimal,
    val category: String,
    val type: String?,
    val location: GeoJsonPoint?
)

data class DetailResponse(
    val asin: String? = null,
    val isbn: String,
)
