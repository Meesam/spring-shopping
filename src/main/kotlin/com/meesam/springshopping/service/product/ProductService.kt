package com.meesam.springshopping.service.product

import com.meesam.springshopping.dto.ProductImageRequest
import com.meesam.springshopping.dto.ProductRequest
import com.meesam.springshopping.dto.ProductResponse
import com.meesam.springshopping.model.Product
import com.meesam.springshopping.model.ProductImages
import com.meesam.springshopping.repository.category.CategoryRepository
import com.meesam.springshopping.repository.product.ProductImageRepository
import com.meesam.springshopping.repository.product.ProductRepository
import com.meesam.springshopping.service.user.CustomUserDetailsService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val productImageRepository: ProductImageRepository,
    private val userDetailsService: CustomUserDetailsService,
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

    fun getAllProduct(): List<ProductResponse> {
        val products = productRepository.findAll().map { product ->
            ProductResponse(
                id = product.id,
                title = product.title,
                description = product.description,
                price = product.price,
                quantity = product.quantity,
                categoryId = product.category.id,
                categoryName = product.category.title,
                createdAt = product.createdAt
            )
        }
        return products
    }

    fun addProductImage(productImageRequest: ProductImageRequest){
        val product = productRepository.findByIdOrNull(productImageRequest.productId)
        product?.let {
            productImageRepository.save(ProductImages(
                imagePath = productImageRequest.imagePath,
                products = product,
                createdAt = LocalDateTime.now()
            ))
        }
    }
}