package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.AttributeRequest
import com.meesam.springshopping.dto.AttributeResponse
import com.meesam.springshopping.dto.ProductAttributeRequest
import com.meesam.springshopping.service.category.CategoryService
import com.meesam.springshopping.service.product.AttributeService
import com.meesam.springshopping.service.product.ProductAttributeService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/attribute")
class AttributeController(private val attributeService: AttributeService, private val productAttributeService: ProductAttributeService) {

    @PostMapping("/create")
    fun addNewAttribute(@Valid @RequestBody attributeRequest: AttributeRequest): ResponseEntity<Boolean> {
        attributeService.addNewAttribute(attributeRequest)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/get")
    fun getAllAttributesByCategoryId(@RequestParam("categoryId") categoryId: Long):ResponseEntity<List<AttributeResponse>> {
        val attributes = attributeService.getAllAttributesByCategory(categoryId)
        return ResponseEntity.ok(attributes)
    }

    @PostMapping("/add-product-attribute")
    fun addProductAttribute(@Valid @RequestBody attributeRequest: ProductAttributeRequest): ResponseEntity<Boolean> {
        productAttributeService.addProductAttribute(attributeRequest)
        return ResponseEntity.ok(true)
    }
}