package com.meesam.springshopping.service.user


import com.meesam.springshopping.dto.UserFavoriteProductRequest
import com.meesam.springshopping.dto.UserProfilePictureRequest
import com.meesam.springshopping.dto.UserRequest
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.dto.UserUpdateRequest
import com.meesam.springshopping.exception_handler.DataAccessProblem
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
import org.slf4j.LoggerFactory
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

    companion object{
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }
    @Transactional
    fun createUser(createUser: UserRequest): UserResponse {
        val user = userRepository.findByEmail(createUser.email)
        user?.let { throw IllegalArgumentException("User already exists with this email: ${createUser.email}") }
        try {
            with(createUser) {
                val user = User(
                    name = name,
                    email = email,
                    password = encoder.encode(password),
                    dob = dob,
                    lastLoginAt = LocalDateTime.now(),
                    role = role ?: "User",
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
        } catch (ex: Exception) {
            logger.error("Could not create user: {}", ex.message)
            throw DataAccessProblem("Could not create user", ex)
        }
    }

    @Transactional
    fun updateUser(userUpdateRequest: UserUpdateRequest) {
        val existUser = userRepository.findByIdOrNull(userUpdateRequest.id)
            ?: throw NotFoundException("user not found")
        try {
            with(userUpdateRequest) {
                userRepository.save(
                    existUser.copy(
                        name = name,
                        dob = dob,
                        profilePicUrl = profilePicUrl
                    )
                )
            }
        } catch (ex: Exception) {
            logger.error("Could not update user: {}", ex.message)
            throw DataAccessProblem("Could not update user", ex)
        }
    }

    fun addUserFavoriteProduct(userFavoriteProductRequest: UserFavoriteProductRequest) {
        val user = userRepository.findByIdOrNull(userFavoriteProductRequest.userId)
            ?: throw NotFoundException("User not found")
        val product = productRepository.findByIdOrNull(userFavoriteProductRequest.productId)
            ?: throw NotFoundException("Product not found")

        try {
            userFavoriteProductRepository.save(
                UserFavoriteProduct(
                    userId = user.id,
                    productId = product.id,
                    createdAt = LocalDateTime.now()
                )
            )
        } catch (e: Exception) {
            logger.error("Could not add user favorite product: {}", e.message)
            throw DataAccessProblem("Could not add user favorite product", e)
        }

    }

    @Transactional
    fun addUserWishListProduct(userFavoriteProductRequest: UserFavoriteProductRequest) {
        val user = userRepository.findByIdOrNull(userFavoriteProductRequest.userId)
            ?: throw NotFoundException("User not found")
        val product = productRepository.findByIdOrNull(userFavoriteProductRequest.productId)
            ?: throw NotFoundException("Product not found")

        try {
            userWishlistRepository.save(
                UserWishList(
                    userId = user.id,
                    productId = product.id,
                    createdAt = LocalDateTime.now()
                )
            )
        } catch (e: Exception) {
            logger.error("Could not add user wishlist product: {}", e.message)
            throw DataAccessProblem("Could not add user wishlist product", e)
        }
    }


    @Transactional
    fun addUserProfilePicture(userProfilePictureRequest: UserProfilePictureRequest) {
        val user = userRepository.findByIdOrNull(userProfilePictureRequest.userId)
            ?: throw NotFoundException("User not found")
        val result = firebaseService.uploadFile(userProfilePictureRequest.profilePicUrl)
        if (result.isNotEmpty()) {
            try {
                userRepository.save(
                    user.copy(
                        profilePicUrl = result
                    )
                )
            } catch (e: Exception) {
                logger.error("Could not add user profile picture: {}", e.message)
                throw DataAccessProblem("Could not add user profile picture", e)
            }
        }
    }
}