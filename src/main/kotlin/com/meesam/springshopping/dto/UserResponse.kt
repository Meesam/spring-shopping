package com.meesam.springshopping.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name :String = "",
    val email: String = "" ,
    val dob: LocalDate? = null,
    val lastLoginAt: LocalDateTime? = null
)
