package com.example.demo.model.book

import java.math.BigDecimal

data class ItemPrice(
    val currency: String,
    val price: BigDecimal,
    val category: String,
    val type: String
)
