package com.example.demo.mapper

// import models.dto.ItemPriceDto
// import models.dto.ReviewDto
// import models.entity.ItemPrice
// import models.entity.Review
import models.dto.AuthorDto
import models.dto.BookDto
import models.dto.DetailDto
import models.dto.RatesDto
import models.entity.Author
import models.entity.Book
import models.entity.Detail
import models.entity.Rates
import org.bson.types.ObjectId

fun BookDto.toBook(): Book = Book(
    bookID = ObjectId().toString(),
    title = this.title,
    authors = this.authors.map { it.toAuthor() },
    rates = this.rates.toRate(),
    abstract = this.abstract,
//    prices = this.prices?.map { it.toItemPrice() },
    details = this.details.toDetail(),
//    reviews = this.reviews?.map { it.toReview() }
)

fun AuthorDto.toAuthor() = Author(
    authorName = this.authorName
)

fun RatesDto.toRate() = Rates(
    rate = this.rate,
    rateAmount = this.rateAmount
)

// fun ItemPriceDto.toItemPrice() = ItemPrice(
//    currency = this.currency,
//    category = this.category,
//    price = this.price,
//    type = this.type
// )

fun DetailDto.toDetail() = Detail(
    isbn = this.isbn,
    asin = this.asin
)

// fun ReviewDto.toReview() = Review(
//    reviewerName = this.reviewerName,
//    reviewContent = this.reviewContent,
//    helpful = this.helpful,
//    rate = this.rate
// )
// TODO DO PO Entity https://github.com/FasterXML/jackson-module-kotlin
