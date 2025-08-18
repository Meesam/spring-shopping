package com.meesam.springshopping.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "product_images")
data class ProductImages(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = true, name = "image_path")
    val imagePath :String,

    @OneToMany(mappedBy = "images", cascade = [CascadeType.ALL], orphanRemoval = true)
    val users: MutableList<Product> = mutableListOf()
)
