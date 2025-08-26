package com.meesam.springshopping.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {

    private val separator = ","

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        // Handle null or empty list
        return attribute?.joinToString(separator)
    }

    override fun convertToEntityAttribute(dbData: String?): List<String>? {
        // Handle null or empty string from DB
        return dbData?.split(separator)?.map { it.trim() }
    }
}
