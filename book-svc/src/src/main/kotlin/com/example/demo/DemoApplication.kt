package com.example.demo

import io.mongock.runner.springboot.EnableMongock
// import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableMongock
@SpringBootApplication
class DemoApplication

// private val logger = LogManager.getLogger(DemoApplication::class.java)
fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
//    logger.info("Application started")
}
