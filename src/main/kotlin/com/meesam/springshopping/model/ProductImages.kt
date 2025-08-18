package com.meesam.springshopping.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "product_images")
data class ProductImages(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = true, name = "image_path")
    val imagePath :String,

    @Column(nullable = false, name = "createdAt")
    val createdAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val products: Product
)
