package com.example.kotlin.dto

data class ClientUpdateDto(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var job: String? = null,
    var position: String? = null
)