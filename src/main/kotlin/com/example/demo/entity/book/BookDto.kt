package com.example.demo.entity.book

import lombok.Data
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "book")
@Data
data class BookDto(
    val uuid: String,
    val title: String,
    val authors: List<Author>,
    val rates: Rates,
    val abstract: String,
    val prices: List<ItemPrice>,
    val details: Detail,
    val reviews: List<Review>?,
)
