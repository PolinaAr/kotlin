package com.example.kotlin.dto

import com.example.kotlin.enums.Gender

data class ClientUpdateDto(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var gender: Gender? = null,
    var job: String? = null,
    var position: String? = null
)