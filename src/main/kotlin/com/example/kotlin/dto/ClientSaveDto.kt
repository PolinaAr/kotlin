package com.example.kotlin.dto

data class ClientSaveDto(
    var firstName: String,
    var lastName: String,
    var email: String,
    var job: String? = null,
    var position: String? = null
)