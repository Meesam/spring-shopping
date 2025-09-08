package com.meesam.springshopping.service.auth

import com.meesam.springshopping.dto.AuthenticationRequest
import com.meesam.springshopping.dto.AuthenticationResponse
import com.meesam.springshopping.dto.RefreshTokenRequest
import com.meesam.springshopping.dto.TokenResponse
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.repository.user.UserRepository
import com.meesam.springshopping.security.JwtProperties
import com.meesam.springshopping.service.user.CustomUserDetailsService
import com.meesam.springshopping.service.user.TokenService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date


@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AuthenticationService::class.java)
    }

    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.email,
                    authRequest.password
                )
            )
            val user = userDetailsService.loadUserByUsername(authRequest.email)
            val userDetails = userRepository.findByEmail(authRequest.email)
            val userCompleteDetails = with(userDetails) {
                UserResponse(
                    id = this?.id ?: 0,
                    name = this?.name ?: "",
                    email = this?.email ?: "",
                    dob = this?.dob ?: LocalDate.now(),
                    lastLoginAt = this?.lastLoginAt ?: LocalDateTime.now(),
                    role = this?.role ?: "",
                    profilePicUrl = this?.profilePicUrl ?: ""
                )
            }
            val accessToken = tokenService.generate(
                userDetails = user,
                expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
            )
            val refreshToken = tokenService.generate(
                userDetails = user,
                expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration),
            )
            return AuthenticationResponse(
                token = accessToken,
                refreshToken = refreshToken,
                user = userCompleteDetails
            )
        } catch (e: AuthenticationException) {
            logger.error("Authentication error: {}", e.message)
            throw DataAccessProblem("Invalid username or password", e)
        }
    }

    fun generateAccessToken(refreshTokenRequest: RefreshTokenRequest): AuthenticationResponse? {
        try {
            tokenService.extractEmail(refreshTokenRequest.token)?.let { email ->
                if (!tokenService.isExpired(refreshTokenRequest.token)) {
                    val foundUser = userDetailsService.loadUserByUsername(email)
                    val userDetails = userRepository.findByEmail(email)
                    val userCompleteDetails = with(userDetails) {
                        UserResponse(
                            id = this?.id ?: 0,
                            name = this?.name ?: "",
                            email = this?.email ?: "",
                            dob = this?.dob ?: LocalDate.now(),
                            lastLoginAt = this?.lastLoginAt ?: LocalDateTime.now(),
                            role = this?.role ?: "",
                            profilePicUrl = this?.profilePicUrl ?: ""
                        )
                    }
                    if (tokenService.isValid(refreshTokenRequest.token, foundUser)) {
                        val accessToken = tokenService.generate(
                            userDetails = foundUser,
                            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
                        )
                        return AuthenticationResponse(
                            token = accessToken,
                            refreshToken = refreshTokenRequest.token,
                            user = userCompleteDetails
                        )
                    }
                }
            }
        } catch (e: JwtException) {
            throw DataAccessProblem("Invalid refresh token", e)
        } catch (e: ExpiredJwtException) {
            throw DataAccessProblem("refresh token is expired", e)
        }
        return null
    }
}