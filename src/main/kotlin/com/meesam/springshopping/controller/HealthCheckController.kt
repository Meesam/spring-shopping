package com.meesam.springshopping.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/health-check")
@Tag(name = "HealthCheck Controller", description = "Endpoints for check health of api")
class HealthCheckController {

    @Operation(summary = "check health of api")
    @GetMapping
    fun CheckApiHelth(): ResponseEntity<String>{
        return ResponseEntity<String>("Spring Shopping is running in good condition", HttpStatus.OK)
    }

}