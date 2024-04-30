package com.example.kotlin.dto.client

import com.example.kotlin.enums.Gender
import jakarta.validation.constraints.Email

data class ClientSaveDto(
    var firstName: String,
    var lastName: String,
    @field:Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$",
        message = "Email should follow email format")
    var email: String,
    var gender: Gender? = null,
    var job: String? = null,
    var position: String? = null
)