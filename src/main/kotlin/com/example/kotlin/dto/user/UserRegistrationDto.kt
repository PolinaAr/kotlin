package com.example.kotlin.dto.user

import com.example.kotlin.annotation.Password
import jakarta.validation.constraints.Email

data class UserRegistrationDto(
    @field:Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$",
        message = "Email should follow email format")
    val email: String,
    val firstName: String,
    val lastName: String,
    @field:Password
    val password: String
)