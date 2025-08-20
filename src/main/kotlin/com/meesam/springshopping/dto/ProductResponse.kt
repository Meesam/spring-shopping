package com.meesam.springshopping.dto

import java.time.LocalDateTime

data class ProductResponse(
    val id: Long = 0,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val categoryId: Long = 0,
    val categoryName: String = "",
    val quantity: Int = 0,
    val createdAt: LocalDateTime? = null
)
