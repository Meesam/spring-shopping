package com.meesam.springshopping.service.user

import com.google.firebase.internal.FirebaseService
import com.meesam.springshopping.dto.UserFavoriteProductRequest
import com.meesam.springshopping.dto.UserProfilePictureRequest
import com.meesam.springshopping.dto.UserRequest
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.dto.UserUpdateRequest
import com.meesam.springshopping.exception_handler.NotFoundException
import com.meesam.springshopping.model.User
import com.meesam.springshopping.model.UserFavoriteProduct
import com.meesam.springshopping.model.UserWishList
import com.meesam.springshopping.repository.product.ProductRepository
import com.meesam.springshopping.repository.product.UserFavoriteProductRepository
import com.meesam.springshopping.repository.product.UserWishlistRepository
import com.meesam.springshopping.repository.user.UserRepository
import com.meesam.springshopping.service.firebase.FirebaseStorageService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    private val userFavoriteProductRepository: UserFavoriteProductRepository,
    private val productRepository: ProductRepository,
    private val firebaseService: FirebaseStorageService,
    private val userWishlistRepository: UserWishlistRepository
) {

    @Transactional
    fun createUser(createUser: UserRequest): UserResponse {
        with(createUser) {
            val user = User(
                name = name,
                email = email,
                password = encoder.encode(password),
                dob = dob,
                lastLoginAt = LocalDateTime.now(),
                role = "User",
                createdAt = LocalDateTime.now()
            )
            val response = userRepository.save(user)
            response.let {
                val userResponse = UserResponse(
                    id = response.id,
                    name = response.name,
                    email = response.email,
                    dob = response.dob,
                    lastLoginAt = response.lastLoginAt,
                    role = response.role
                )
                return userResponse
            }
        }
    }

    @Transactional
    fun updateUser(userUpdateRequest: UserUpdateRequest) {
        val existUser = userRepository.findByIdOrNull(userUpdateRequest.id)
            ?: throw NotFoundException("user not found")
        with(userUpdateRequest) {
            userRepository.save(
                existUser.copy(
                    name = name,
                    dob = dob,
                    profilePicUrl = profilePicUrl
                )
            )
        }
    }

    fun addUserFavoriteProduct(userFavoriteProductRequest: UserFavoriteProductRequest) {
        val user = userRepository.findByIdOrNull(userFavoriteProductRequest.userId)
            ?: throw NotFoundException("User not found")
        val product = productRepository.findByIdOrNull(userFavoriteProductRequest.productId)
            ?: throw NotFoundException("Product not found")

        userFavoriteProductRepository.save(
            UserFavoriteProduct(
                userId = user.id,
                productId = product.id,
                createdAt = LocalDateTime.now()
            )
        )
    }

    @Transactional
    fun addUserWishListProduct(userFavoriteProductRequest: UserFavoriteProductRequest) {
        val user = userRepository.findByIdOrNull(userFavoriteProductRequest.userId)
            ?: throw NotFoundException("User not found")
        val product = productRepository.findByIdOrNull(userFavoriteProductRequest.productId)
            ?: throw NotFoundException("Product not found")

        userWishlistRepository.save(
            UserWishList(
                userId = user.id,
                productId = product.id,
                createdAt = LocalDateTime.now()
            )
        )
    }


    @Transactional
    fun addUserProfilePicture(userProfilePictureRequest: UserProfilePictureRequest){
        val user = userRepository.findByIdOrNull(userProfilePictureRequest.userId)
            ?: throw NotFoundException("User not found")
        val result = firebaseService.uploadFile(userProfilePictureRequest.profilePicUrl)
        if(result.isNotEmpty()){
            userRepository.save(
                user.copy(
                    profilePicUrl = result
                )
            )
        }

    }
}