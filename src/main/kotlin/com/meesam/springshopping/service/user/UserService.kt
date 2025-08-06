package com.meesam.springshopping.service.user

import com.meesam.springshopping.dto.UserRequest
import com.meesam.springshopping.dto.UserResponse
import com.meesam.springshopping.model.User
import com.meesam.springshopping.repository.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(private val userRepository: UserRepository, private val encoder: PasswordEncoder) {

    fun createUser(createUser: UserRequest): UserResponse{
        with(createUser){
            val user = User(
                name = name,
                email = email,
                password = encoder.encode(password),
                dob = dob,
                lastLoginAt = LocalDateTime.now(),
                role = "ADMIN",
            )
           val response = userRepository.save(user)
            response.let {
                val userResponse = UserResponse(
                    id = response.id,
                    name = response.name,
                    email = response.email,
                    dob = response.dob,
                    lastLoginAt = response.lastLoginAt,
                    role = "Admin"
                )
                return userResponse
            }
        }
    }
}