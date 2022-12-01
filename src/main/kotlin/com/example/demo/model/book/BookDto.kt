package com.example.demo.model.book

data class BookDto (
    val uuid:String,
    val title:String,
    val authors:List<Author>,
    val rates:Rates,
    val abstract:String,
    val prices:List<ItemPrice>,
    val details:Detail,
    val reviews:List<Review>?,
    )