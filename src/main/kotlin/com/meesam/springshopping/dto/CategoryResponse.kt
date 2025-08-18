package com.meesam.springshopping.dto

import java.time.LocalDateTime

data class CategoryResponse(
    val id: Long = 0,
    val title:String = "",
    val createdAt:LocalDateTime? = null
)