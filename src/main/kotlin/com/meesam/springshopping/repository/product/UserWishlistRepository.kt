package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.UserWishList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserWishlistRepository: JpaRepository<UserWishList, Long> {
}