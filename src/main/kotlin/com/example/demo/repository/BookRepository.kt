package com.example.demo.repository

import com.example.demo.entity.book.BookDto
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : MongoRepository<BookDto, Int>
//TODO https://www.fivetran.com/blog/when-to-use-nosql-mongodb
