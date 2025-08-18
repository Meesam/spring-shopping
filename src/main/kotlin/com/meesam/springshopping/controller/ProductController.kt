package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.ProductRequest
import com.meesam.springshopping.service.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/product")
class ProductController(private val productService: ProductService) {


    @PostMapping("/create")
    fun createProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<Boolean> {
        productService.createProduct(productRequest)
        return ResponseEntity.ok(true)
    }
}