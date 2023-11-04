package com.example.book

import io.mongock.runner.springboot.EnableMongock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableMongock
@SpringBootApplication
class BookApplication

fun main(args: Array<String>) {
    runApplication<BookApplication>(*args)
}
