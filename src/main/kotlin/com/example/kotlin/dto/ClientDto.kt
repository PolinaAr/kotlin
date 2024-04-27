package com.example.kotlin.dto

data class ClientDto(
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var job: String? = null,
    var position: String? = null
)