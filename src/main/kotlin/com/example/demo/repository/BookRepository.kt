package com.example.demo.repository

import com.example.demo.entity.book.BookDto
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
abstract class BookRepository : MongoRepository<BookDto, Int>
