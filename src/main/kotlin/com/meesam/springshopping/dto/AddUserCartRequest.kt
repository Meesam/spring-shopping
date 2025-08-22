package com.meesam.springshopping.dto

data class AddUserCartRequest(
    val title:String? = null,
    val userId:Long = 0,
    val quantity:Long = 0,
    val productId:Long = 0
)
