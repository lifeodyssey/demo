package com.example.demo.repository

import models.entity.Book
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface BookRepository : MongoRepository<Book, String>{
    fun findByBookID(bookId: String):Optional<Book>
}
// TODO https://www.fivetran.com/blog/when-to-use-nosql-mongodb
