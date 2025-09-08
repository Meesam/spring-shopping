package com.meesam.springshopping.dto

data class AuthenticationResponse(
    val token: String,
    val refreshToken: String? = null,
    val user:UserResponse? = null
)
