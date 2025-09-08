package com.meesam.springshopping.model

import com.fasterxml.jackson.annotation.JsonIgnore
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
@Table(name = "user_address")
  data class UserAddress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, name = "address_name")
    var addressName :String,

    @Column(nullable = false, name = "street")
    var street :String,

    @Column(nullable = false, name = "state")
    var state :String,

    @Column(nullable = false, name = "city")
    var city :String,

    @Column(nullable = true, name = "country")
    var country :String? = null,

    @Column(nullable = true, name = "pin")
    var pin :String,

    @Column(nullable = true, name = "nearby")
    var nearby :String? = null,

    @Column(nullable = false, name = "createdAt")
    var createdAt: LocalDateTime? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var users: User
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserAddress
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    override fun toString(): String {
        return "UserAddress(id=$id, addressName='$addressName', street='$street', state='$state', city='$city', country=$country, pin='$pin', nearby=$nearby, createdAt=$createdAt, users=$users')"
    }
 }
