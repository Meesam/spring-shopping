package com.meesam.springshopping.dto

data class ProductRequest(
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: Long = 0,
    val quantity: Int = 0
)
