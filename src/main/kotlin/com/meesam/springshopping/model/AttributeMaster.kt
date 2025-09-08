package com.meesam.springshopping.model

import com.fasterxml.jackson.annotation.JsonIgnore
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
import org.hibernate.Hibernate
import java.time.LocalDateTime


@Entity
@Table(name = "attribute_master")
 data class AttributeMaster(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, name = "title")
    var title: String,

    @Column(nullable = true, name = "description")
    var description: String? = null,

    @Column(nullable = false, name = "createdAt")
    var createdAt: LocalDateTime? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var category: Category,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attributes", cascade = [CascadeType.ALL])
    var productAttributes: MutableSet<ProductAttributes> = mutableSetOf()

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AttributeMaster
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    override fun toString(): String {
        return "AttributeMaster(id=$id, title='$title')"
    }
 }

