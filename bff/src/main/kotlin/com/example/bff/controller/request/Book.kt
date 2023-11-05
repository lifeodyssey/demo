package com.example.bff.controller.request

import java.math.BigDecimal
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.mapping.MongoId

data class Book(
    @MongoId @Id var bookId: String?,
    val title: String,
    val authors: List<Author>,
    val rates: Rates,
    val abstract: String,
    val bookItems: List<BookItem>? = null,
    val details: Detail,
)

data class Author(
    val authorName: String
)

data class Detail(
    val asin: String? = "",
    val isbn: String,
)

data class BookItem(
    val currency: String,
    val price: BigDecimal,
    val category: String,
    val type: String?,
    val location: GeoJsonPoint?
)

data class Rates(
    val rate: BigDecimal = BigDecimal.ZERO,
    val rateAmount: Int = 0
)
