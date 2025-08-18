package com.meesam.springshopping.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_wishlist")
data class UserWishList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, name = "product_id")
    val productId :Long,

    @Column(nullable = false, name = "user_id")
    val userId :Long,

    @Column(nullable = false, name = "createdAt")
    val createdAt: LocalDateTime? = null,
)
