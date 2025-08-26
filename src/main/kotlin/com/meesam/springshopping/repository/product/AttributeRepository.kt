package com.meesam.springshopping.repository.product

import com.meesam.springshopping.model.AttributeMaster
import com.meesam.springshopping.model.UserAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface AttributeRepository: JpaRepository<AttributeMaster, Long> {

    @Query("select am from attribute_master am where am.category.id= = :categoryId")
    fun getAllAttributeByCategoryId(@Param("categoryId") categoryId: Long): List<AttributeMaster>
}