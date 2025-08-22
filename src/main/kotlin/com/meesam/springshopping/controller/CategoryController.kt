package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.CategoryRequest
import com.meesam.springshopping.dto.CategoryResponse
import com.meesam.springshopping.service.category.CategoryService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/category")
class CategoryController(private val categoryService: CategoryService) {

    companion object{
        private val logger = LoggerFactory.getLogger(CategoryController::class.java)
    }
    @PostMapping("/create")
    fun createCategory(@RequestBody categoryRequest: CategoryRequest): ResponseEntity<Boolean> {
        logger.info("Received a request to the /api/category/create endpoint.")
        categoryService.createCategory(categoryRequest)
        return ResponseEntity.ok(true)
    }
    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<List<CategoryResponse>>{
        logger.info("Received a request to the /api/category/all endpoint.")
        val result = categoryService.getAllCategories()
        return ResponseEntity(result, HttpStatus.OK)
    }
}