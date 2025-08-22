package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.ProductImageRequest
import com.meesam.springshopping.dto.ProductRequest
import com.meesam.springshopping.dto.ProductResponse
import com.meesam.springshopping.service.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/product")
class ProductController(private val productService: ProductService) {

    @PostMapping("/create")
    fun createProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<Boolean> {
        productService.createProduct(productRequest)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/all")
    fun getAllProduct(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getAllProduct())
    }

    @PostMapping("/addImage")
    fun addProductImages(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("productId") productId: Long
    ): ResponseEntity<Boolean> {

        val productImageRequest = ProductImageRequest(file, productId)
        productService.addProductImage(productImageRequest)
        return ResponseEntity.ok(true)
    }
}