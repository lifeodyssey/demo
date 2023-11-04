package com.example.book.mapper

import com.example.book.controller.dto.AuthorRequest
import com.example.book.controller.dto.BookItemRequest
import com.example.book.controller.dto.BookRequest
import com.example.book.controller.dto.DetailRequest
import com.example.book.controller.dto.RatesRequest
import com.example.book.repository.entity.Author
import com.example.book.repository.entity.Book
import com.example.book.repository.entity.BookItem
import com.example.book.repository.entity.Detail
import com.example.book.repository.entity.Rates

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
