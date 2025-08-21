package com.meesam.springshopping.dto

import org.springframework.web.multipart.MultipartFile

data class UserProfilePictureRequest(
    val profilePicUrl: MultipartFile,
    val userId: Long = 0
)
