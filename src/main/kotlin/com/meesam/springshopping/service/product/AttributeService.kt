package com.meesam.springshopping.service.product

import com.meesam.springshopping.dto.AttributeRequest
import com.meesam.springshopping.dto.AttributeResponse
import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.model.AttributeMaster
import com.meesam.springshopping.repository.category.CategoryRepository
import com.meesam.springshopping.repository.product.AttributeRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AttributeService(
    private val attributeRepository: AttributeRepository,
    private val categoryRepository: CategoryRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(AttributeService::class.java)
    }

    fun addNewAttribute(attributeRequest: AttributeRequest) {
        val category = categoryRepository.findByIdOrNull(attributeRequest.categoryId as Long)
            ?: throw NoSuchElementException("Category not found")
        try {
            with(attributeRequest) {
                attributeRepository.save(
                    AttributeMaster(
                        title = title,
                        description = description,
                        category = category,
                        createdAt = LocalDateTime.now()
                    )
                )
            }
        } catch (ex: Exception) {
            logger.error("Could not create attribute: {}", ex.message)
            throw DataAccessProblem("Could not create attribute", ex)
        }
    }

    fun getAllAttributesByCategory(categoryId: Long): List<AttributeResponse> {
        val attributes = attributeRepository.getAllAttributeByCategoryId(categoryId = categoryId)
        if (attributes.isEmpty()) {
            return emptyList()
        }
        return attributes.map {
            AttributeResponse(
                id = it.id,
                title = it.title,
                description = it.description,
                createdAt = it.createdAt ?: LocalDateTime.now(),
                categoryId = it.category.id,
                categoryName = it.category.title
            )
        }
    }

    fun getAllAttributes(): List<AttributeResponse> {
        val attributes = attributeRepository.findAll()
        if (attributes.isEmpty()) {
            return emptyList()
        }
        return attributes.map {
            AttributeResponse(
                id = it.id,
                title = it.title,
                description = it.description,
                categoryId = it.category.id,
                categoryName = it.category.title,
                createdAt = it.createdAt ?: LocalDateTime.now()
            )
        }
    }
}