package com.meesam.springshopping.service.user

import com.meesam.springshopping.dto.UserAddressRequest
import com.meesam.springshopping.dto.UserAddressResponse
import com.meesam.springshopping.model.UserAddress
import com.meesam.springshopping.repository.user.UserAddressRepository
import com.meesam.springshopping.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserAddressService(
    private val userAddressRepository: UserAddressRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun addNewAddress(userAddressRequest: UserAddressRequest) {
        val user = userRepository.findByIdOrNull(userAddressRequest.userId)
            ?: throw IllegalArgumentException("User not found")

        with(userAddressRequest) {
            userAddressRepository.save(
                UserAddress(
                    addressName = address,
                    street = street,
                    state = state,
                    city = city,
                    pin = pin,
                    nearby = nearBy,
                    createdAt = LocalDateTime.now(),
                    users = user
                )
            )
        }
    }

    fun getUserWithAddress(id: Long): List<UserAddressResponse> {
        val result = userAddressRepository.getAllAddressByUserId(id)
        return result.map {
            UserAddressResponse(
                id = it.id,
                address = it.addressName,
                city = it.city,
                state = it.state,
                pin = it.pin,
                userId = it.users.id,
                userName = it.users.name,
                createdAt = it.createdAt,
                street = it.street,
                nearBy = it.nearby
            )
        }
    }
}