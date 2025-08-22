package com.meesam.springshopping.service.category

import com.meesam.springshopping.dto.CategoryRequest
import com.meesam.springshopping.dto.CategoryResponse
import com.meesam.springshopping.model.Category
import com.meesam.springshopping.repository.category.CategoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    @Transactional
    fun createCategory(categoryRequest: CategoryRequest){
        val category = Category(title = categoryRequest.title, createdAt = LocalDateTime.now())
        categoryRepository.save<Category>(category)
    }

    @Transactional
    fun getAllCategories(): List<CategoryResponse> {
        return categoryRepository.findAll().map {
            CategoryResponse(it.id, it.title, createdAt = it.createdAt)
        }.sortedBy { it.title }
    }


}