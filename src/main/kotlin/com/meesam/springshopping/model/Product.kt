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
import java.util.Optional

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, name = "title")
    var title: String,

    @Column(nullable = true, name = "description")
    var description: String,

    @Column(nullable = false, name = "price")
    var price: Double,

    @Column(nullable = false, name = "quantity")
    var quantity: Int,

    @Column(nullable = false, name = "createdAt")
    val createdAt: LocalDateTime? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "products", cascade = [CascadeType.ALL])
    val productImages: MutableSet<ProductImages> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category
)