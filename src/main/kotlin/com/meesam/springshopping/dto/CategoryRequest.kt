package com.meesam.springshopping.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CategoryRequest(

    @field:NotBlank(message = "title cannot be blank")
    @field:Size(min = 3, max = 100, message = "title must be between 3 and 100 characters")
    val title: String = "",
)
