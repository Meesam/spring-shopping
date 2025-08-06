package com.meesam.springshopping.repository.user

import com.meesam.springshopping.model.UserAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAddressRepository: JpaRepository<UserAddress, Long> {
}