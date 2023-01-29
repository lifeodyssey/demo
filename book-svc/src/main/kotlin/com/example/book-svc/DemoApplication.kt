package com.example.`book-svc`

import io.mongock.runner.springboot.EnableMongock
// import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableMongock
@SpringBootApplication
@EnableFeignClients
class DemoApplication

// private val logger = LogManager.getLogger(DemoApplication::class.java)
fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
//    logger.info("Application started")
}
