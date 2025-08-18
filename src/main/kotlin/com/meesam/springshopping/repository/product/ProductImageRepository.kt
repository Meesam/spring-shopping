package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.ProductImages
import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageRepository: JpaRepository<ProductImages, Long> {
}