package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.CartProducts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CartProductsRepository: JpaRepository<CartProducts, Long> {
}