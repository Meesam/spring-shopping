package com.meesam.springshopping.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
        httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/health-check", "/api/auth/**" ,"/api/auth/refresh", "/error")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/product/**", "/api/category/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .fullyAuthenticated()

            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

}