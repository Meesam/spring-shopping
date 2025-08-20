package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.UserFavoriteProduct
import org.springframework.data.jpa.repository.JpaRepository

interface UserFavoriteProductRepository : JpaRepository<UserFavoriteProduct, Long> {

    //fun addUserFavoriteProduct(userId: Long, productId: Long)
}