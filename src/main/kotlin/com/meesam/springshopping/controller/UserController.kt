package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.AddUserCartRequest
import com.meesam.springshopping.dto.UserAddressRequest
import com.meesam.springshopping.dto.UserAddressResponse
import com.meesam.springshopping.dto.UserAddressUpdateRequest
import com.meesam.springshopping.dto.UserFavoriteProductRequest
import com.meesam.springshopping.dto.UserProfilePictureRequest
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.dto.UserUpdateRequest
import com.meesam.springshopping.service.user.UserAddressService
import com.meesam.springshopping.service.user.UserCartService
import com.meesam.springshopping.service.user.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/user")
class UserController(
    private val userAddressService: UserAddressService,
    private val userService: UserService,
    private val userCartService: UserCartService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping("/update-user")
    fun updateUser(@Valid @RequestBody userUpdateRequest: UserUpdateRequest): ResponseEntity<Boolean> {
        logger.info("Received a request to the /api/user/update-user endpoint.")
        userService.updateUser(userUpdateRequest)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/getUser")
    fun getUserByid(@RequestParam("id") id: Long): ResponseEntity<UserResponse> {
        logger.info("Received a request to the /api/getUser endpoint.")
        return ResponseEntity.ok(userService.getUserProfile(id))
    }

    @PostMapping("/add-address")
    fun addNewAddress(@Valid @RequestBody userAddressRequest: UserAddressRequest): ResponseEntity<Boolean> {
        logger.info("Received a request to the /api/user/add-address endpoint.")
        userAddressService.addNewAddress(userAddressRequest)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/address")
    fun getAllUserAddress(@RequestParam("id") id: Long): ResponseEntity<List<UserAddressResponse>> {
        logger.info("Received a request to the /api/address endpoint.")
        return ResponseEntity.ok(userAddressService.getUserWithAddress(id))
    }

    @PostMapping("/update-address")
    fun updateAddress(@Valid @RequestBody userAddressUpdateRequest: UserAddressUpdateRequest): ResponseEntity<Boolean> {
        userAddressService.updateAddress(userAddressUpdateRequest)
        return ResponseEntity.ok(true)
    }

    @DeleteMapping("/delete-address/{id}")
    fun deleteAddress(@PathVariable("id") id: Long): ResponseEntity<Boolean> {
        userAddressService.deleteAddress(id)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/add-favorite-product")
    fun addUserFavoriteProduct(@Valid @RequestBody userFavoriteProductRequest: UserFavoriteProductRequest): ResponseEntity<Boolean> {
        userService.addUserFavoriteProduct(userFavoriteProductRequest)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/add-product-to-wishlist")
    fun addUserProductToWishList(@Valid @RequestBody userFavoriteProductRequest: UserFavoriteProductRequest): ResponseEntity<Boolean> {
        userService.addUserWishListProduct(userFavoriteProductRequest)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/add-profile-picture")
    fun addUserProfilePicture(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("userId") userId: Long
    ): ResponseEntity<Boolean> {
        val request = UserProfilePictureRequest(
            userId = userId,
            profilePicUrl = file
        )
        userService.addUserProfilePicture(request)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/add-to-cart")
    fun addProductToCart(@Valid @RequestBody addUserCartRequest: AddUserCartRequest): ResponseEntity<Boolean> {
        userCartService.addUserCart(addUserCartRequest)
        return ResponseEntity.ok(true)
    }
}