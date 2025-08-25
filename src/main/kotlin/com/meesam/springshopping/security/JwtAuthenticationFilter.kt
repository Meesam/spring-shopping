package com.meesam.springshopping.security

import com.meesam.springshopping.exception_handler.DataAccessProblem
import com.meesam.springshopping.service.user.CustomUserDetailsService
import com.meesam.springshopping.service.user.TokenService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Instant

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService
): OncePerRequestFilter() {

    companion object {
        private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            //check if the request have Authorization in Header
            val authHeader : String? = request.getHeader("Authorization")
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response)
                return
            }
            // extract tokenvalue
            val jwtToken =  authHeader.substringAfter("Bearer ")
            val email = tokenService.extractEmail(jwtToken)
            email?.let {
                if(SecurityContextHolder.getContext().authentication == null) {
                    val foundUser = userDetailsService.loadUserByUsername(email)
                    if(tokenService.isValid(jwtToken, foundUser)){
                        updateContext(foundUser, request)
                    }
                }
            }
        }catch (ex: ExpiredJwtException) {
            unauthorized(response, "invalid_token", "Token expired at ${ex.claims?.expiration}", 401)
            return
        } catch (ex: JwtException) {
            // Covers malformed, signature invalid, unsupported, etc.
            unauthorized(response, "invalid_token", ex.message ?: "Invalid token", 401)
            return
        }
        filterChain.doFilter(request, response)
    }

    private fun updateContext(
        foundUser: UserDetails,
        request: HttpServletRequest
    ) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken

    }

    // Sends a 401 JSON error and sets the standard WWW-Authenticate header for Bearer tokens.
    private fun unauthorized(
        response: HttpServletResponse,
        error: String,
        description: String,
        status: Int
    ) {
        if (response.isCommitted) return
        response.status = status
        response.contentType = "application/json"
        response.setHeader(
            "WWW-Authenticate",
            """Bearer error="$error", error_description="${description.replace("\"", "'")}""""
        )
        val payload = """{"timestamp":"${Instant.now()}","status":$status,"error":"$error","message":"$description"}"""
        log.error("Authentication failed: $payload")

        response.writer.use { it.write(payload) }
    }
}



