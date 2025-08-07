package com.meesam.springshopping.repository.user

import com.meesam.springshopping.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    //fun findByUsername(username: String): User?

    fun findByEmail(email: String): User?
}