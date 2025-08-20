package com.meesam.springshopping.dto

data class UserAddressUpdateRequest(
    val id: Long = 0,
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val pin: String = "",
    val street:String = "",
    val nearBy:String? = null,
    val userId: Long = 0
)
