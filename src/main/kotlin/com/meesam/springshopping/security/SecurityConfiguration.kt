package com.meesam.springshopping.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
            .cors { corsConfigurationSource() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/health-check", "/api/auth/**" ,"/api/auth/refresh-token", "/error")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/product/**", "/api/category/**","/api/attribute/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .anyRequest()
                    .fullyAuthenticated()

            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            // Use allowedOriginPatterns if you need wildcards with credentials
            // e.g., addAllowedOriginPattern("https://*.example.com")
            addAllowedOrigin("http://localhost:5173")
            addAllowedOrigin("http://127.0.0.1:3000")
            addAllowedHeader(CorsConfiguration.ALL)
            addAllowedMethod(CorsConfiguration.ALL)
            allowCredentials = true
            // Optional: expose headers your frontend needs to read
            addExposedHeader("Authorization")
            addExposedHeader("Content-Disposition")
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }


}