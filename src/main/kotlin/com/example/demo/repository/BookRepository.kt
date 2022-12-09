package com.example.demo.repository

import models.entity.Book
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : MongoRepository<Book, Int>
// TODO https://www.fivetran.com/blog/when-to-use-nosql-mongodb
