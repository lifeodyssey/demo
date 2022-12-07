package com.example.demo.entity.book

import java.math.BigDecimal

data class Rates(
    val rate: BigDecimal= BigDecimal.ZERO,
    val rateAmount: Int=0
)
