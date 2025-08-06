package com.meesam.springshopping.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("jwt")
data class JwtProperties(
  val key: String,
  val accessTokenExpriration: Long,
  val refreshTokenExpiration: Long
)
