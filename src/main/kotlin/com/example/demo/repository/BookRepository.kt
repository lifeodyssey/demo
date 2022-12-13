package com.example.demo.repository

import models.entity.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String>
// TODO https://www.fivetran.com/blog/when-to-use-nosql-mongodb
