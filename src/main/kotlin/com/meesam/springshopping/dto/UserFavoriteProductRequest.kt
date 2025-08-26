package com.meesam.springshopping.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class UserFavoriteProductRequest(
    @field:NotNull(message = "productId cannot be null")
    @field:Min(value = 1, message = "productId must be greater than zero")
    val productId: Long = 0,

    @field:Min(value = 1, message = "userId must be greater than zero")
    @field:NotNull(message = "userId cannot be null")
    val userId: Long = 0
)
