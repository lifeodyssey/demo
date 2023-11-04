package com.example.book.repository

import com.example.book.repository.entity.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String>
// TODO https://www.fivetran.com/blog/when-to-use-nosql-mongodb
