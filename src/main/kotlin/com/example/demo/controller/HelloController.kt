package com.example.demo.controller

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class HelloController {
    @RequestMapping("/")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getHello(): String {
        return "Hello"
    }
}
