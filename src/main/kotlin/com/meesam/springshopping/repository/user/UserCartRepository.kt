package com.meesam.springshopping.repository.user

import com.meesam.springshopping.model.UserCart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCartRepository: JpaRepository<UserCart, Long> {
}