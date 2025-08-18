package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.CategoryRequest
import com.meesam.springshopping.dto.CategoryResponse
import com.meesam.springshopping.service.category.CategoryService
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

    @PostMapping("/create")
    fun createCategory(@RequestBody categoryRequest: CategoryRequest): ResponseEntity<Boolean> {
        categoryService.createCategory(categoryRequest)
        return ResponseEntity.ok(true)
    }
    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<List<CategoryResponse>>{
        val result = categoryService.getAllCategories()
        return ResponseEntity(result, HttpStatus.OK)
    }
}