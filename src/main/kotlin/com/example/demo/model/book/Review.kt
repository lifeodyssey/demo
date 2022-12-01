package com.example.demo.model.book

import java.math.BigDecimal

data class Review(
    val reviewerName:String,
    val reviewContent:String,
    val helpful:Int,
    val rate:BigDecimal
)
