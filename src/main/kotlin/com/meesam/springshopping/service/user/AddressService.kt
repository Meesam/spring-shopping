package com.meesam.springshopping.service.user

import com.meesam.springshopping.repository.user.UserAddressRepository
import org.springframework.stereotype.Service


@Service
class AddressService(private val addressRepository: UserAddressRepository) {
}