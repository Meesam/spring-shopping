package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.ProductAttributes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductAttributeRepository: JpaRepository<ProductAttributes, Long> {
}