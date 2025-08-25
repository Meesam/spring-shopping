package com.meesam.springshopping.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class AddUserCartRequest(
    val title:String? = null,

    @field:Positive(message = "userId must be greater than zero")
    val userId:Long = 0,

    @field:NotBlank(message = "quantity cannot be blank")
    @field:NotNull(message = "quantity cannot be null")
    @field:Positive(message = "quantity must be greater than zero")
    @field:Min(value = 1, message = "Quantity must be at least 1")
    val quantity:Long = 0,

    @field:Positive(message = "productId must be greater than zero")
    val productId:Long = 0
)
