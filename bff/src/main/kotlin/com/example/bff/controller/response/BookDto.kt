package com.example.bff.controller.response
import com.example.bff.config.GeoJsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.validation.constraints.NotBlank
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.math.BigDecimal

data class BookDto(
    @NotBlank(message = "Title is required")
    val title: String,
    @NotBlank(message = "Author is required")
    val authors: List<AuthorDto>,
    val rates: RatesDto,
    val abstract: String = "",
    val bookItems: List<BookItemDto>? = null,
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

data class BookItemDto(
    val currency: String,
    val price: BigDecimal,
    val category: String,
    val type: String?,
    @JsonDeserialize(using = GeoJsonDeserializer::class)
    val location: GeoJsonPoint?
)

data class DetailDto(
    val asin: String? = null,
    @NotBlank(message = "ISBN is required")
    val isbn: String,
)
