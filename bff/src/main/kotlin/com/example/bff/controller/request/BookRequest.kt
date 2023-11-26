package com.example.bff.controller.request
import com.example.bff.config.GeoJsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.validation.constraints.NotBlank
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

data class BookRequest(
    @NotBlank(message = "Title is required")
    val title: String,
    @NotBlank(message = "Author is required")
    val authors: List<AuthorRequest>,
    val rates: RatesRequest,
    val abstract: String = "",
    val bookItems: List<BookItemRequest>? = null,
    @NotBlank(message = "Details is required")
    val details: DetailRequest,
)

data class AuthorRequest(
    val authorName: String
)

data class RatesRequest(
    val rate: Double = 0.0,
    val rateAmount: Int = 0
)

data class BookItemRequest(
    val currency: String,
    val price: BigDecimal,
    val category: String,
    val type: String?,
    @JsonDeserialize(using = GeoJsonDeserializer::class)
    val location: GeoJsonPoint?
)

data class DetailRequest(
    val asin: String? = null,
    @NotBlank(message = "ISBN is required")
    val isbn: String,
)
