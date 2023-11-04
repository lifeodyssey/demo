package com.example.book.mapper

import com.example.book.controller.dto.AuthorResponse
import com.example.book.controller.dto.BookItemResponse
import com.example.book.controller.dto.BookResponse
import com.example.book.controller.dto.DetailResponse
import com.example.book.controller.dto.RatesResponse
import com.example.book.repository.entity.Author
import com.example.book.repository.entity.Book
import com.example.book.repository.entity.BookItem
import com.example.book.repository.entity.Detail
import com.example.book.repository.entity.Rates

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
