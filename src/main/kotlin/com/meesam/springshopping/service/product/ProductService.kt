package com.meesam.springshopping.service.product

import com.meesam.springshopping.dto.ProductImageRequest
import com.meesam.springshopping.dto.ProductRequest
import com.meesam.springshopping.model.Product
import com.meesam.springshopping.repository.category.CategoryRepository
import com.meesam.springshopping.repository.product.ProductImageRepository
import com.meesam.springshopping.repository.product.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val productImageRepository: ProductImageRepository
) {

    fun createProduct(productRequest: ProductRequest) {
        val category = categoryRepository.findByIdOrNull(productRequest.category)
        category?.let {
            val product = Product(
                title = productRequest.title,
                quantity = productRequest.quantity,
                price = productRequest.price,
                description = productRequest.description,
                category = category,
                createdAt = LocalDateTime.now()
            )
            productRepository.save(product)
        }
    }

    fun addProductImages(productImageRequest: ProductImageRequest){

    }
}