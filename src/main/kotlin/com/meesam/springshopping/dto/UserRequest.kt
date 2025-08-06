package com.meesam.springshopping.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import java.time.LocalDate
import java.time.LocalDateTime

data class UserRequest(
    val name :String = "",
    val email: String = "" ,
    val password: String = "",
    val dob: LocalDate,
    val lastLoginAt: LocalDateTime? = null
)
