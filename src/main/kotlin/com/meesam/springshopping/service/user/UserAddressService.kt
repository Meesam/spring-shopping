package com.meesam.springshopping.service.user

import com.meesam.springshopping.dto.UserAddressRequest
import com.meesam.springshopping.dto.UserAddressResponse
import com.meesam.springshopping.dto.UserAddressUpdateRequest
import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.model.UserAddress
import com.meesam.springshopping.repository.user.UserAddressRepository
import com.meesam.springshopping.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserAddressService(
    private val userAddressRepository: UserAddressRepository,
    private val userRepository: UserRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(UserAddressService::class.java)
    }
    @Transactional
    fun addNewAddress(userAddressRequest: UserAddressRequest) {
        val user = userRepository.findByIdOrNull(userAddressRequest.userId)
            ?: throw IllegalArgumentException("User not found")

        try {
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
                        country = country,
                        users = user
                    )
                )
            }
        }catch (e: Exception){
            logger.error("Could not add new address: {}", e.message)
            throw DataAccessProblem("Could not add new address", e)
        }

    }

    @Transactional
    fun getUserWithAddress(id: Long): List<UserAddressResponse> {
        val result = userAddressRepository.getAllAddressByUserId(id)
        try {
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
                    country = it.country,
                    street = it.street,
                    nearBy = it.nearby
                )
            }
        }catch (e: Exception){
            logger.error("Could not get User With Address: {}", e.message)
            throw DataAccessProblem("Could not get User With Address", e)
        }

    }

    @Transactional
    fun updateAddress(userAddressUpdateRequest: UserAddressUpdateRequest){
        val user = userRepository.findByIdOrNull(userAddressUpdateRequest.userId)
            ?: throw IllegalArgumentException("User not found")

        val existAddress = userAddressRepository.findByIdOrNull(userAddressUpdateRequest.id)
            ?: throw IllegalArgumentException("address not found")

        try {
            with(userAddressUpdateRequest) {
                userAddressRepository.save(
                    existAddress.copy(
                        street = street,
                        state = state,
                        city = city,
                        pin = pin,
                        country = country,
                        nearby = nearBy,
                        id = id,
                        users = user,
                        addressName = address
                    )
                )
            }
        }catch (e: Exception){
            logger.error("could not update address: {}", e.message)
            throw DataAccessProblem("could not update address", e)
        }

    }

    @Transactional
    fun deleteAddress(id: Long){
        val existAddress = userAddressRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("address not found")
        try {
            userAddressRepository.delete(existAddress)
        }catch (e: Exception){
            logger.error("could not delete address: {}", e.message)
            throw DataAccessProblem("could not delete address", e)
        }
    }
}