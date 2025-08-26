package com.meesam.springshopping.controller

import com.meesam.springshopping.dto.CategoryResponse
import com.meesam.springshopping.service.category.CategoryService
import org.springframework.test.web.servlet.MockMvc
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime



@ExtendWith(MockKExtension::class)
class CategoryControllerTest {

    private lateinit var mockMvc: MockMvc

    @MockK
    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setup() {
        val controller = CategoryController(categoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun `should return all categories its exists`() {
        // 1. Arrange: Define the behavior of the mocked service
        val category = listOf(
            CategoryResponse(1, "Electronics", LocalDateTime.now()),
            CategoryResponse(2, "Mobile Phone", LocalDateTime.now()),
            CategoryResponse(3, "Jeans", LocalDateTime.now())
        )
        every { categoryService.getAllCategories() } returns category

        // 2. Act & Assert: Perform the simulated HTTP GET request
        mockMvc.perform(get("/api/category/all")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk) // Check for HTTP 200 OK
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Electronics"))
    }

    @Test
    fun `should return empty list if no categories exists`() {
        // 1. Arrange: Define the behavior of the mocked service
        val category = emptyList<CategoryResponse>()
        every { categoryService.getAllCategories() } returns category

        // 2. Act & Assert: Perform the simulated HTTP GET request
        mockMvc.perform(get("/api/category/all")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk) // Check for HTTP 200 OK
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `should create new category`() {
        // 1. Arrange: Define the behavior of the mocked service
        every { categoryService.createCategory(any()) } returns Unit

        // 2. Act & Assert: Perform the simulated HTTP Post request
        mockMvc.perform(post("/api/category/create")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("""{"title":"All"}""")
        ).andExpect(status().isOk)
    }
}