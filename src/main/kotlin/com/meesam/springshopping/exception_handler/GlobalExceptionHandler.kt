package com.meesam.springshopping.exception_handler


import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Resource not found",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Invalid argument provided",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DuplicateResourceException::class)
    fun handleDuplicateResourceException(ex: DuplicateResourceException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message ?: "A resource with this name already exists.",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(DataAccessProblem::class)
    fun handleDataAccessProblem(ex: DataAccessProblem): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An unexpected database error occurred.",
            timestamp = System.currentTimeMillis()
        )
        // Log the full stack trace for debugging purposes
        ex.printStackTrace()
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: Long
)

class NotFoundException(message: String) : RuntimeException(message)

class DuplicateResourceException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
class DataAccessProblem(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
