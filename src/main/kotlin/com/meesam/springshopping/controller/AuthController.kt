package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.AuthenticationRequest
import com.meesam.springshopping.dto.AuthenticationResponse
import com.meesam.springshopping.dto.RefreshTokenRequest
import com.meesam.springshopping.dto.TokenResponse
import com.meesam.springshopping.dto.UserRequest
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.service.auth.AuthenticationService
import com.meesam.springshopping.service.user.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userService: UserService, private val authenticationService: AuthenticationService) {

    @PostMapping("/register")
    fun createUser(@Valid @RequestBody userRequest: UserRequest): ResponseEntity<UserResponse>{
        val result = userService.createUser(userRequest)
        return ResponseEntity(result, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse{
       return authenticationService.authentication(authenticationRequest)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@Valid @RequestBody refreshTokenRequest: RefreshTokenRequest): TokenResponse{
        return authenticationService.generateAccessToken(refreshTokenRequest)
    }
}