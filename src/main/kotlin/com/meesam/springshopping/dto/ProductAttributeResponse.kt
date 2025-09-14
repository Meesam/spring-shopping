package com.meesam.springshopping.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductAttributeResponse(
    val id: Long?,
    val productId: Long,
    val attributeId: Long,
    val attributeTitle: String,
    val values: String?,
    val price: Double?,
    val createdAt: LocalDateTime
)

