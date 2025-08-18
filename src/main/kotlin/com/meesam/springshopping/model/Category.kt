package com.meesam.springshopping.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name = "category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, name = "title")
    var title: String,

    @Column(nullable = false, name = "createdAt")
    val createdAt: LocalDateTime? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = [CascadeType.ALL])
    val product: MutableSet<Product> = mutableSetOf(),
)
