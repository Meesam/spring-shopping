package com.meesam.springshopping.security

import com.meesam.springshopping.service.user.CustomUserDetailsService
import com.meesam.springshopping.service.user.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService
): OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
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
               filterChain.doFilter(request, response)
           }
        }
    }
}

private fun updateContext(
    foundUser: UserDetails,
    request: HttpServletRequest
) {
    val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
    SecurityContextHolder.getContext().authentication = authToken

}
