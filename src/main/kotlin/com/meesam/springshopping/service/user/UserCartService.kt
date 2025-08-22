package com.meesam.springshopping.service.user

import com.meesam.springshopping.dto.AddUserCartRequest
import com.meesam.springshopping.model.CartProducts
import com.meesam.springshopping.model.UserCart
import com.meesam.springshopping.repository.product.CartProductsRepository
import com.meesam.springshopping.repository.product.ProductRepository
import com.meesam.springshopping.repository.user.UserCartRepository
import com.meesam.springshopping.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserCartService(
    private val userCartRepository: UserCartRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductsRepository
) {

    @Transactional
    fun addUserCart(userCartRequest: AddUserCartRequest){
        val user = userRepository.findByIdOrNull(userCartRequest.userId)
            ?: throw IllegalArgumentException("User not found")
        val product = productRepository.findByIdOrNull(userCartRequest.productId)
            ?: throw IllegalArgumentException("Product not found")

           val result = with(userCartRequest) {
                userCartRepository.save(
                    UserCart(
                        title = if(title.isNullOrEmpty()) "My Cart" else title,
                        users = user,
                        createdAt = LocalDateTime.now(),
                    )
                )
            }

        with(userCartRequest){
            cartProductRepository.save(
                CartProducts(
                    cartId = result.id,
                    productId = productId,
                    quantity = quantity,
                    createdAt = LocalDateTime.now()
                )
            )
        }

    }
}