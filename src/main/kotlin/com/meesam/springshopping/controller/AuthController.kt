package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.UserRequest
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("/register")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{
        val result = userService.createUser(userRequest)
        return ResponseEntity(result, HttpStatus.CREATED)
    }
}