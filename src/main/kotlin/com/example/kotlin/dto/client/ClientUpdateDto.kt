package com.example.kotlin.dto.client

import jakarta.validation.constraints.Email

data class ClientUpdateDto(
    var firstName: String? = null,
    var lastName: String? = null,
    @field:Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$",
        message = "Email should follow email format")
    var email: String? = null,
    var job: String? = null,
    var position: String? = null
)