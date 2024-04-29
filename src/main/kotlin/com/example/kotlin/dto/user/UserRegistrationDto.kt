package com.example.kotlin.dto.user

data class UserRegistrationDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
)