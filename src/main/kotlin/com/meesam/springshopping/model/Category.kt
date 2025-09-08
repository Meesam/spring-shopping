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
import org.hibernate.Hibernate
import java.time.LocalDateTime


@Entity
@Table(name = "category")
 data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true, name = "title")
    var title: String,

    @Column(nullable = false, name = "createdAt")
    var createdAt: LocalDateTime? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = [CascadeType.ALL])
    var product: MutableSet<Product> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = [CascadeType.ALL])
    var attributes: MutableSet<AttributeMaster> = mutableSetOf(),
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Category
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    override fun toString(): String {
        return "Category(id=$id, title='$title')"
    }

}
