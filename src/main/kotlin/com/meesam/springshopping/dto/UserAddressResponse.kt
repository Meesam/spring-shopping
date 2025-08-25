package com.meesam.springshopping.dto

import java.time.LocalDateTime

data class UserAddressResponse(
    val id: Long = 0,
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val pin: String = "",
    val street:String = "",
    val nearBy:String? = null,
    val country:String? = null,
    val userId: Long = 0,
    val userName:String = "",
    val createdAt: LocalDateTime? = null
)
