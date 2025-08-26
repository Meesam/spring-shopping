package com.meesam.springshopping.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime

data class UserRequest(

    @field:NotBlank(message = "name cannot be blank")
    @field:Size(min = 3, max = 100, message = "name must be between 3 and 100 characters")
    val name :String = "",

    @field:NotBlank(message = "email cannot be blank")
    @field:Email(message = "invalid email address")
    val email: String = "" ,

    @field:NotBlank(message = "password cannot be blank")
    val password: String = "",

    val role:String? = null,

    val dob: LocalDate,
    val lastLoginAt: LocalDateTime? = null
)
