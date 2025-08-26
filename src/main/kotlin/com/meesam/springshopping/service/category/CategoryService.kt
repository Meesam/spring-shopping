package com.meesam.springshopping.service.category

import com.meesam.springshopping.dto.CategoryRequest
import com.meesam.springshopping.dto.CategoryResponse
import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.exception_handler.DuplicateResourceException
import com.meesam.springshopping.model.Category
import com.meesam.springshopping.repository.category.CategoryRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.sql.SQLException
import java.time.LocalDateTime

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    companion object{
        private val logger = LoggerFactory.getLogger(CategoryService::class.java)
    }
    @Transactional
    fun createCategory(categoryRequest: CategoryRequest) {
        val category = Category(title = categoryRequest.title, createdAt = LocalDateTime.now())
        try {
            categoryRepository.save<Category>(category)
        }catch (ex: DataIntegrityViolationException) {
            val sqlState = (rootCause(ex) as? SQLException)?.sqlState
            // 23505 is a common SQLState for unique violation (e.g., PostgreSQL/H2)
            if (sqlState == "23505" || ex.message?.contains("unique", true) == true) {
                logger.error("Duplicate category title: '{}' (sqlState={})", category.title, sqlState)
                throw DuplicateResourceException("Category title already exists", ex)
            }
            logger.error("Data integrity violation creating category '{}': {}", category.title, ex.message)
            throw DataAccessProblem("Could not create category", ex)
        }catch (ex: Exception) {
            logger.error("Unexpected error creating category: {}", ex.message)
            throw DataAccessProblem("Could not create category", ex)
        }
    }

    @Transactional
    fun getAllCategories(): List<CategoryResponse> {
        try {
            return categoryRepository.findAll().map {
                CategoryResponse(it.id, it.title, createdAt = it.createdAt)
            }.sortedBy { it.title }
        }catch (ex: DataAccessProblem) {
            logger.error("Could not get categories: {}", ex.message)
            throw DataAccessProblem("Could not get categories", ex)
        }
    }

    private fun rootCause(t: Throwable): Throwable {
        var cause = t
        while (cause.cause != null && cause.cause !== cause) {
            cause = cause.cause!!
        }
        return cause
    }



}