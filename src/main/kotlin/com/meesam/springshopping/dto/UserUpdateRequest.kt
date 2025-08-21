package com.meesam.springshopping.dto

import java.time.LocalDate

data class UserUpdateRequest(
    val id: Long = 0,
    val name: String= "",
    val dob: LocalDate,
    val profilePicUrl: String? = null
)
