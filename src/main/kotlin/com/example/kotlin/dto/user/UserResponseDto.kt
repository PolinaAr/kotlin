package com.example.kotlin.dto.user

data class UserResponseDto (
    val id: Long?,
    val email: String,
    val firstName: String,
    val lastName: String
)