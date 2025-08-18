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
    val id: Long = 0,

    @Column(nullable = false, name = "name")
    val name :String,

    @Column(nullable = false, unique = true, name = "email")
    val email: String,

    @JsonIgnore
    @Column(nullable = false, name = "password")
    val password: String,

    @Column(nullable = false, name = "date_of_birth")
    val dob: LocalDate,

    @Column(nullable = true, name = "last_login_at")
    val lastLoginAt: LocalDateTime? = null,

    @Column(nullable = false, name = "role")
    val role: String,

    @Column(nullable = false, name = "createdAt")
    val createdAt: LocalDateTime? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = [CascadeType.ALL])
    val address: MutableSet<UserAddress> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users", cascade = [CascadeType.ALL])
    val cart: MutableSet<UserCart> = mutableSetOf(),

)
