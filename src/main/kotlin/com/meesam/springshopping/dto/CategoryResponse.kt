package com.meesam.springshopping.dto

import java.time.LocalDateTime

data class CategoryResponse(
    val id: Long? = null,
    val title:String = "",
    val createdAt:LocalDateTime? = null
)