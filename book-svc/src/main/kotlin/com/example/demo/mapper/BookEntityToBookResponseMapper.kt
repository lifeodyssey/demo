package com.example.demo.mapper

import models.dto.AuthorResponse
import models.dto.BookItemResponse
import models.dto.BookResponse
import models.dto.DetailResponse
import models.dto.RatesResponse
import models.entity.Author
import models.entity.Book
import models.entity.BookItem
import models.entity.Detail
import models.entity.Rates

fun Book.toBookResponse(): BookResponse = BookResponse(
    bookId = this.bookId!!,
    title = this.title,
    authors = this.authors.map { it.toAuthorResponse() },
    rates = this.rates.toRateResponse(),
    abstract = this.abstract,
    bookItems = this.bookItems?.map { it.toBookItemResponse() },
    details = this.details.toDetailResponse(),
)

fun Author.toAuthorResponse() = AuthorResponse(
    authorName = this.authorName
)

fun Rates.toRateResponse() = RatesResponse(
    rate = this.rate,
    rateAmount = this.rateAmount
)

fun BookItem.toBookItemResponse() = BookItemResponse(
    currency = this.currency,
    category = this.category,
    price = this.price,
    type = this.type,
    location = this.location
)

fun Detail.toDetailResponse() = DetailResponse(
    isbn = this.isbn,
    asin = this.asin
)
