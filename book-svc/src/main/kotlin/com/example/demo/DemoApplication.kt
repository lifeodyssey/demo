package com.example.demo

import io.mongock.runner.springboot.EnableMongock
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableMongock
@SpringBootApplication
@Slf4j
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
