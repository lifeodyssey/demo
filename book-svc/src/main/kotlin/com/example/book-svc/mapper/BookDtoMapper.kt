package com.example.`book-svc`.mapper

// import models.dto.ItemPriceDto
// import models.dto.ReviewDto
// import models.entity.ItemPrice
// import models.entity.Review
import models.dto.AuthorDto
import models.dto.BookDto
import models.dto.BookItemDto
import models.dto.DetailDto
import models.dto.RatesDto
import models.entity.Author
import models.entity.Book
import models.entity.BookItem
import models.entity.Detail
import models.entity.Rates

fun BookDto.toBook(): Book = Book(
    bookId = null,
    title = this.title,
    authors = this.authors.map { it.toAuthor() },
    rates = this.rates.toRate(),
    abstract = this.abstract,
    bookItems = this.bookItems?.map { it.toBookItem() },
    details = this.details.toDetail(),
)

fun AuthorDto.toAuthor() = Author(
    authorName = this.authorName
)

fun RatesDto.toRate() = Rates(
    rate = this.rate,
    rateAmount = this.rateAmount
)

fun BookItemDto.toBookItem() = BookItem(
    currency = this.currency,
    category = this.category,
    price = this.price,
    type = this.type,
    location = this.location
)

fun DetailDto.toDetail() = Detail(
    isbn = this.isbn,
    asin = this.asin
)
