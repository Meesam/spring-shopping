package com.meesam.springshopping.dto

import java.time.LocalDateTime

data class AttributeResponse(
    var id: Long,
    var title: String,
    var description :String? = null,
    var categoryId: Long,
    var createdAt: LocalDateTime,
    var categoryName: String
)
