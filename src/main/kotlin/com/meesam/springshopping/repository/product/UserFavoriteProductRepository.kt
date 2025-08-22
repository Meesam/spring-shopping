package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.UserFavoriteProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserFavoriteProductRepository : JpaRepository<UserFavoriteProduct, Long> {

}