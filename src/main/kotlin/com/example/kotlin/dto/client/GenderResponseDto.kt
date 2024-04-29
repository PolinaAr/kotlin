package com.example.kotlin.dto.client

data class GenderResponseDto(
    val count : Int,
    val name: String,
    val gender: String,
    val probability: Float
)