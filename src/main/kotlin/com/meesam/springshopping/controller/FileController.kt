package com.meesam.springshopping.controller

import com.meesam.springshopping.service.firebase.FirebaseStorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/file")
class FileController(private val firebaseStorageService: FirebaseStorageService) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val fileUrl = firebaseStorageService.uploadFile(file)
        return ResponseEntity.ok(fileUrl)
    }

    @DeleteMapping("/delete")
    fun deleteFile(@RequestParam("fileName") fileName: String): ResponseEntity<String> {
        val isDeleted = firebaseStorageService.deleteFile(fileName)
        return if (isDeleted) {
            ResponseEntity.ok("File deleted successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to delete file.")
        }
    }
}