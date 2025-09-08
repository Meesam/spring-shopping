package com.meesam.springshopping.model

import com.fasterxml.jackson.annotation.JsonIgnore
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
@Table(name = "product_attributes")
data class ProductAttributes(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, name = "values")
    var values : String,

    @Column(nullable = true, name = "price")
    var price : Double? = null,

    @Column(nullable = false, name = "createdAt")
    var createdAt: LocalDateTime? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var products: Product,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var attributes: AttributeMaster

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductAttributes
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    override fun toString(): String {
        return "ProductAttributes(id=$id)"
    }
}
