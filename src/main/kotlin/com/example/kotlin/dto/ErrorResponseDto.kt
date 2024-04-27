package com.example.kotlin.dto

import java.time.LocalDateTime

data class ErrorResponseDto(
    val error: String,
    val timestamp: LocalDateTime,
    val status: Int,
    val message: String
)