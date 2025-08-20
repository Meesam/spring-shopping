package com.meesam.springshopping.repository.user

import com.meesam.springshopping.dto.UserAddressResponse
import com.meesam.springshopping.model.UserAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserAddressRepository: JpaRepository<UserAddress, Long> {
    @Query("select ua from UserAddress ua where ua.users.id = :userId")
    fun getAllAddressByUserId(@Param("userId") userId: Long): List<UserAddress>
}