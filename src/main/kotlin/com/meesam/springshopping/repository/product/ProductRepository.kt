package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.Product
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = ["productImages", "category"])
    @Query("select p from Product p")
    fun findAllWithImages(): List<Product>

    @EntityGraph(attributePaths = ["productImages", "category","productAttributes"])
    @Query("select p from Product p where p.id = :id")
    fun findProductById(id: Long): Product?

}