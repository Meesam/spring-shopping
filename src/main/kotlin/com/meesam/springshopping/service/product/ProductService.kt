package com.meesam.springshopping.service.product

import com.meesam.springshopping.dto.ProductImageRequest
import com.meesam.springshopping.dto.ProductRequest
import com.meesam.springshopping.dto.ProductResponse
import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.model.Product
import com.meesam.springshopping.model.ProductImages
import com.meesam.springshopping.repository.category.CategoryRepository
import com.meesam.springshopping.repository.product.ProductImageRepository
import com.meesam.springshopping.repository.product.ProductRepository
import com.meesam.springshopping.service.firebase.FirebaseStorageService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val productImageRepository: ProductImageRepository,
    private val firebaseService: FirebaseStorageService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(ProductService::class.java)
    }

    @Transactional
    fun createProduct(productRequest: ProductRequest) {
        val category = categoryRepository.findByIdOrNull(productRequest.category)
        category?.let {
            try {
                val product = Product(
                    title = productRequest.title,
                    quantity = productRequest.quantity,
                    price = productRequest.price,
                    description = productRequest.description,
                    category = category,
                    createdAt = LocalDateTime.now()
                )
                productRepository.save(product)
            }catch (ex: Exception){
                logger.error("Could not create product: {}", ex.message)
                throw DataAccessProblem("Could not create product", ex)
            }
        } ?: throw IllegalArgumentException("category not found")
    }

    @Transactional
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
                createdAt = product.createdAt,
                productImages =  product.productImages.toList()
            )
        }
        return products
    }

    @Transactional
    fun addProductImage(productImageRequest: ProductImageRequest) {
        val product = productRepository.findByIdOrNull(productImageRequest.productId)
            ?: throw IllegalArgumentException("Product not found")
        val result = firebaseService.uploadFile(productImageRequest.imagePath)
        if (result.isNotEmpty()) {
            try {
                productImageRepository.save(
                    ProductImages(
                        products = product,
                        imagePath = result,
                        createdAt = LocalDateTime.now()
                    )
                )
            }catch (e: Exception){
                logger.error("Could not add product image: {}", e.message)
                throw DataAccessProblem("Could not add product image", e)
            }

        }
    }
}