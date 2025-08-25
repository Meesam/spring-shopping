package com.meesam.springshopping.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserFavoriteProductRequest(
    @field:NotNull(message = "productId cannot be null")
    @field:NotBlank(message = "productId cannot be blank")
    @field:Min(value = 1, message = "productId must be greater than zero")
    val productId: Long = 0,

    @field:Min(value = 1, message = "userId must be greater than zero")
    @field:NotNull(message = "userId cannot be null")
    @field:NotBlank(message = "userId cannot be blank")
    val userId: Long = 0
)
