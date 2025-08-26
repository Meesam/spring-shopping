package com.meesam.springshopping.dto

import jakarta.validation.constraints.Min

data class ProductAttributeRequest(
    @field:Min(value = 1, message = "productId must be greater than zero")
    val productId: Long = 0,

    @field:Min(value = 1, message = "attributeId must be greater than zero")
    val attributeId: Long = 0,

    val values: List<String>
)
