package com.meesam.springshopping.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class CartProducts(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false,name = "cart_id")
    val cartId : Long,

    @Column(nullable = false,name = "product_id")
    val productId : Long,

    @Column(nullable = false,name = "quantity")
    val quantity : Long,
)
