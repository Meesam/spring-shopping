package com.meesam.springshopping.dto

import com.meesam.springshopping.model.ProductAttributes
import com.meesam.springshopping.model.ProductImages
import java.time.LocalDateTime

data class ProductResponse(
    val id: Long? = null,
    val title: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val categoryId: Long? = null,
    val categoryName: String = "",
    val quantity: Int = 0,
    val createdAt: LocalDateTime? = null,
    val productImages: List<ProductImages> = emptyList(),
    val productAttributes: List<ProductAttributes> = emptyList()
)
