package com.meesam.springshopping.service.product

import com.meesam.springshopping.dto.ProductAttributeRequest
import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.exception_handler.NotFoundException
import com.meesam.springshopping.model.ProductAttributes
import com.meesam.springshopping.repository.product.AttributeRepository
import com.meesam.springshopping.repository.product.ProductAttributeRepository
import com.meesam.springshopping.repository.product.ProductRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductAttributeService(
    private val productAttributeRepository: ProductAttributeRepository,
    private val productRepository: ProductRepository,
    private val attributeRepository: AttributeRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(ProductAttributeService::class.java)
    }

    @Transactional
    fun addProductAttribute(productAttributeRequest: ProductAttributeRequest) {
       val product = productRepository.findByIdOrNull(productAttributeRequest.productId)
            ?: throw NotFoundException("Product not found")

       val attributesMaster = attributeRepository.findByIdOrNull(productAttributeRequest.attributeId)
            ?: throw NotFoundException("Attribute not found")

        try {
            with(productAttributeRequest) {
                productAttributeRepository.save(
                    ProductAttributes(
                        createdAt = LocalDateTime.now(),
                        values = values,
                        price = price,
                        products = product,
                        attributes = attributesMaster
                    )
                )
            }
        }catch (e: Exception){
            logger.error("Could not add product attribute: {}", e.message)
            throw DataAccessProblem("Could not add product attribute", e)
        }
    }
}