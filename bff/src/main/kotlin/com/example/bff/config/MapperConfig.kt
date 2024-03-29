package com.example.bff.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object MapperConfig {
    private val mapper = ObjectMapper().registerKotlinModule()
    fun getMapper() = this.mapper
}
