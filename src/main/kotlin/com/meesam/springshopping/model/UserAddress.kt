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
@Table(name = "user_address")
data class UserAddress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, name = "address_name")
    val addressName :String,

    @Column(nullable = false, name = "street")
    val street :String,

    @Column(nullable = false, name = "state")
    val state :String,

    @Column(nullable = false, name = "city")
    val city :String,

    @Column(nullable = true, name = "pin")
    val pin :String,

    @Column(nullable = true, name = "nearby")
    val nearby :String,

    @OneToMany(mappedBy = "address", cascade = [CascadeType.ALL], orphanRemoval = true)
    val users: MutableList<User> = mutableListOf()
)
