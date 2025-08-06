package com.meesam.springshopping.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/health-check")
class HealthCheckController {

    @GetMapping
    fun CheckApiHelth(): ResponseEntity<String>{
        return ResponseEntity<String>("Spring Shopping is running in good condition", HttpStatus.OK)
    }

}