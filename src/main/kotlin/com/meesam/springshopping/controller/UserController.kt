package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.UserAddressRequest
import com.meesam.springshopping.dto.UserAddressResponse
import com.meesam.springshopping.dto.UserAddressUpdateRequest
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


@RestController
@RequestMapping("/api/user")
class UserController(private val userAddressService: UserAddressService) {

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
    fun updateAddress(@RequestBody userAddressUpdateRequest: UserAddressUpdateRequest):ResponseEntity<Boolean>{
        userAddressService.updateAddress(userAddressUpdateRequest)
        return ResponseEntity.ok(true)
    }

    @DeleteMapping("/delete-address/{id}")
    fun deleteAddress(@PathVariable("id") id: Long): ResponseEntity<Boolean>{
        userAddressService.deleteAddress(id)
        return ResponseEntity.ok(true)
    }
}