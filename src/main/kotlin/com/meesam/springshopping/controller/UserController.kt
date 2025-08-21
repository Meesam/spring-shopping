package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.UserAddressRequest
import com.meesam.springshopping.dto.UserAddressResponse
import com.meesam.springshopping.dto.UserAddressUpdateRequest
import com.meesam.springshopping.dto.UserFavoriteProductRequest
import com.meesam.springshopping.dto.UserProfilePictureRequest
import com.meesam.springshopping.dto.UserUpdateRequest
import com.meesam.springshopping.service.user.UserAddressService
import com.meesam.springshopping.service.user.UserService
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
class UserController(private val userAddressService: UserAddressService, private val userService: UserService) {


    @PostMapping("/update-user")
    fun updateUser(@RequestBody userUpdateRequest: UserUpdateRequest): ResponseEntity<Boolean> {
        userService.updateUser(userUpdateRequest)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/add-address")
    fun addNewAddress(@RequestBody userAddressRequest: UserAddressRequest): ResponseEntity<Boolean> {
        userAddressService.addNewAddress(userAddressRequest)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/address")
    fun getAllUserAddress(@RequestParam("id") id: Long): ResponseEntity<List<UserAddressResponse>> {
        return ResponseEntity.ok(userAddressService.getUserWithAddress(id))
    }

    @PostMapping("/update-address")
    fun updateAddress(@RequestBody userAddressUpdateRequest: UserAddressUpdateRequest): ResponseEntity<Boolean> {
        userAddressService.updateAddress(userAddressUpdateRequest)
        return ResponseEntity.ok(true)
    }

    @DeleteMapping("/delete-address/{id}")
    fun deleteAddress(@PathVariable("id") id: Long): ResponseEntity<Boolean> {
        userAddressService.deleteAddress(id)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/add-favorite-product")
    fun addUserFavoriteProduct(@RequestBody userFavoriteProductRequest: UserFavoriteProductRequest): ResponseEntity<Boolean> {
        userService.addUserFavoriteProduct(userFavoriteProductRequest)
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
}