package com.example.demo.mapper

import models.dto.AuthorRequest
import models.dto.BookItemRequest
import models.dto.BookRequest
import models.dto.DetailRequest
import models.dto.RatesRequest
import models.entity.Author
import models.entity.Book
import models.entity.BookItem
import models.entity.Detail
import models.entity.Rates

fun BookRequest.toBookEntity(): Book = Book(
    bookId = null,
    title = this.title,
    authors = this.authors.map { it.toAuthorEntity() },
    rates = this.rates.toRateEntity(),
    abstract = this.abstract,
    bookItems = this.bookItems?.map { it.toBookItemEntity() },
    details = this.details.toDetailEntity(),
)

fun AuthorRequest.toAuthorEntity() = Author(
    authorName = this.authorName
)

fun RatesRequest.toRateEntity() = Rates(
    rate = this.rate,
    rateAmount = this.rateAmount
)

fun BookItemRequest.toBookItemEntity() = BookItem(
    currency = this.currency,
    category = this.category,
    price = this.price,
    type = this.type,
    location = this.location
)

fun DetailRequest.toDetailEntity() = Detail(
    isbn = this.isbn,
    asin = this.asin
)
