package com.example.book.repository.entity

import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.math.BigDecimal

@Document(collection = "book")
data class Book(
    @MongoId
    var bookId: String?,
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
