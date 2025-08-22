package com.meesam.springshopping.dto

import org.springframework.web.multipart.MultipartFile

data class ProductImageRequest(
    val imagePath: MultipartFile,
    val productId: Long = 0
)
