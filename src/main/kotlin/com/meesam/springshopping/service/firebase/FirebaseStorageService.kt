package com.meesam.springshopping.service.firebase

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.firebase.cloud.StorageClient
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URL
import java.util.UUID

@Service
class FirebaseStorageService {
    fun uploadFile(file: MultipartFile): String {
        if (file.isEmpty) {
            throw IllegalArgumentException("File cannot be empty.")
        }
        val bucket = StorageClient.getInstance().bucket()
        val fileName = "${UUID.randomUUID()}-${file.originalFilename}"

        val blob = bucket.create(fileName, file.bytes, file.contentType)

        val downloadUrl: URL = URL("https://firebasestorage.googleapis.com/v0/b/" +
                "${bucket.name}/o/${blob.name}?alt=media")

        return downloadUrl.toString()
    }

    fun deleteFile(fileName: String): Boolean {
        val bucket = StorageClient.getInstance().bucket()
        val blob = bucket.get(fileName)
        return blob.delete()
    }
}