package com.example.bff.controller.response

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.mapping.MongoId
import java.math.BigDecimal

data class BookResponse(
    @MongoId @Id var bookId: String?,
    val title: String,
    val authors: List<AuthorResponse>,
    val rates: RatesResponse,
    val abstract: String,
    val bookItems: List<BookItemResponse>? = null,
    val details: DetailResponse,
)

data class AuthorResponse(
    val authorName: String
)

data class DetailResponse(
    val asin: String? = "",
    val isbn: String,
)

data class BookItemResponse(
    val currency: String,
    val price: BigDecimal,
    val category: String,
    val type: String?,
    val location: GeoJsonPoint?
)

data class RatesResponse(
    val rate: BigDecimal = BigDecimal.ZERO,
    val rateAmount: Int = 0
)
