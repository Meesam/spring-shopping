package com.meesam.springshopping.service.auth

import com.meesam.springshopping.dto.AuthenticationRequest
import com.meesam.springshopping.dto.AuthenticationResponse
import com.meesam.springshopping.security.JwtProperties
import com.meesam.springshopping.service.user.CustomUserDetailsService
import com.meesam.springshopping.service.user.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import java.util.Date


@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) {

    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.email,
                    authRequest.password
                )
            )
            val user = userDetailsService.loadUserByUsername(authRequest.email)
            val accessToken = tokenService.generate(
                userDetails = user,
                expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
            )
            return AuthenticationResponse(
                token = accessToken
            )
        } catch (e: AuthenticationException) {
            throw e
        }
    }

}