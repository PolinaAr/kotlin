package com.example.kotlin.dto

import com.example.kotlin.enums.Gender

data class ClientDto(
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var gender: Gender,
    var job: String? = null,
    var position: String? = null
)