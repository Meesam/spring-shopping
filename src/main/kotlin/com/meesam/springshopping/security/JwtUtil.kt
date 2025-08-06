package com.meesam.springshopping.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtTokenService(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration.ms}") private val expirationMs: Long
) {

    private fun getSignInKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = mapOf("roles" to userDetails.authorities.map { it.authority })
        return createToken(claims, userDetails.username)
    }

    private fun createToken(customClaims: Map<String, Any>, subject: String): String {
        val now = Date(System.currentTimeMillis())
        val expirationDate = Date(System.currentTimeMillis() + expirationMs)

        val builder = Jwts.builder()
            .setSubject(subject) // Standard claim
            .setIssuedAt(now)      // Standard claim
            .setExpiration(expirationDate) // Standard claim

        // Add custom claims using 'claim()'
        customClaims.forEach { (key, value) ->
            builder.claim(key, value)
        }

        return builder
            .signWith(getSignInKey(), Jwts.SIG.HS256)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}