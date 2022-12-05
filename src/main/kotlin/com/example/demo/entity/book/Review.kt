package com.example.demo.entity.book

import java.math.BigDecimal

data class Review(
    val reviewerName: String,
    val reviewContent: String,
    val helpful: Int,
    val rate: BigDecimal
)
