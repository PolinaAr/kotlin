package com.example.kotlin.dto

import com.example.kotlin.enums.Gender

data class ClientSaveDto(
    var firstName: String,
    var lastName: String,
    var email: String,
    var gender: Gender? = null,
    var job: String? = null,
    var position: String? = null
)