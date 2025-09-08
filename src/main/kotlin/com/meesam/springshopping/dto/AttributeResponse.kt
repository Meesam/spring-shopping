package com.meesam.springshopping.dto

import java.time.LocalDateTime

data class AttributeResponse(
    var id: Long? = null,
    var title: String,
    var description :String? = null,
    var categoryId: Long? = null,
    var createdAt: LocalDateTime,
    var categoryName: String
)
