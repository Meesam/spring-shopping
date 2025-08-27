package com.meesam.springshopping.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "product_attributes")
data class ProductAttributes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, name = "product_id")
    val productId :Long,

    @Column(nullable = false, name = "attribute_id")
    val attributeId :Long,

    @Column(nullable = false, name = "values")
    val values : String,

    @Column(nullable = false, name = "price")
    val price : Double,

    @Column(nullable = false, name = "createdAt")
    val createdAt: LocalDateTime? = null,
)
