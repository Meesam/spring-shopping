package com.meesam.springshopping.dto

data class UserFavoriteProductRequest(
    val productId: Long = 0,
    val userId: Long = 0
)
