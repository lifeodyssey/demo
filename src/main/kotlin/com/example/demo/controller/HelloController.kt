package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {
    @RequestMapping("/")
    @ResponseBody
    fun getHello(): String {
        return "Hello blue/green deployment"
    }
}
