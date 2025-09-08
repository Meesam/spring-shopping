package com.meesam.springshopping.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.Hibernate
import java.time.LocalDateTime


@Entity
@Table(name = "user_favorite_product")
 data class UserFavoriteProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, name = "product_id")
    var productId :Long? = null,

    @Column(nullable = false, name = "user_id")
    var userId :Long? = null,

    @Column(nullable = false, name = "createdAt")
    var createdAt: LocalDateTime? = null,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserFavoriteProduct
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    override fun toString(): String {
        return "UserFavoriteProduct(id=$id)"
    }
 }
