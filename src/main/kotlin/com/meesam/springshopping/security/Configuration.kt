package com.meesam.springshopping.security
import com.meesam.springshopping.repository.user.UserRepository
import com.meesam.springshopping.service.user.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.core.env.Environment
import java.io.IOException
import org.springframework.core.io.ClassPathResource
import java.io.InputStream
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration(private val env: Environment) {

     @Bean
     fun userDetailsService(userRepository: UserRepository): UserDetailsService =
         CustomUserDetailsService(userRepository)

     @Bean
     fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository))
                it.setPasswordEncoder(encoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun firebaseApp(): FirebaseApp {
        try {
            val bucketName = env.getProperty("FIREBASE_STORAGE_BUCKET_NAME")
            val serviceAccount: InputStream = ClassPathResource("spring-shopping-32af2-firebase-adminsdk-fbsvc-e2f4911b19.json").inputStream

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(bucketName.toString())
                .build()

            return FirebaseApp.initializeApp(options)
        } catch (e: IOException) {
            throw RuntimeException("Failed to initialize Firebase: " + e.message, e)
        }
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Spring Shopping API's Endpoints")
                    .version("1.0")
                    .description("API documentation for a Spring Shopping application built with Kotlin.")
                    .termsOfService("http://swagger.io/terms/")
                    .license(
                        License().name("Apache 2.0")
                            .url("http://springdoc.org")
                    )
            )
    }
 }