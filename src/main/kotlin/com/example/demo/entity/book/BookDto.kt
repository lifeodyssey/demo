package com.example.demo.entity.book

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import lombok.Data
import nonapi.io.github.classgraph.json.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "book")
@Data
data class BookDto(

    @Id @JsonSerialize var bookID:UUID?=null,
    @JsonSerialize val title: String,
    @JsonSerialize val authors: List<Author>,
    @JsonSerialize val rates: Rates,
    @JsonSerialize val abstract: String,
    @JsonSerialize val prices: List<ItemPrice>?=null,
    @JsonSerialize val details: Detail,
    @JsonSerialize val reviews: List<Review>?=null,
)
