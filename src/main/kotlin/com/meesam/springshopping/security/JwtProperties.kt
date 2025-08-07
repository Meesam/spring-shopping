package com.meesam.springshopping.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
  val secretKey: String,
  val accessTokenExpiration: Long,
  val refreshTokenExpiration: Long
)
