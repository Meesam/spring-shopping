package com.meesam.springshopping.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
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
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
 data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, name = "name")
    var name :String,

    @Column(nullable = false, unique = true, name = "email")
    var email: String,

    @JsonIgnore
    @Column(nullable = false, name = "password")
    var password: String,

    @Column(nullable = false, name = "date_of_birth")
    var dob: LocalDate,

    @Column(nullable = true, name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null,

    @Column(nullable = false, name = "role")
    var role: String,

    @Column(nullable = true, name = "profile_pic_url")
    var profilePicUrl: String? = null,

    @Column(nullable = false, name = "createdAt")
    var createdAt: LocalDateTime? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = [CascadeType.ALL])
    var address: MutableSet<UserAddress> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = [CascadeType.ALL])
    var cart: MutableSet<UserCart> = mutableSetOf(),

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', password', dob=$dob, lastLoginAt=$lastLoginAt, role='$role', profilePicUrl=$profilePicUrl, createdAt=$createdAt, address=$address, cart=$cart')"
    }
 }
