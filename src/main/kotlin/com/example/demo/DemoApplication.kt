package com.example.demo

import io.mongock.runner.springboot.EnableMongock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class,
        MongoAutoConfiguration::class,
        MongoDataAutoConfiguration::class
    ]
)
@EnableMongock
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
