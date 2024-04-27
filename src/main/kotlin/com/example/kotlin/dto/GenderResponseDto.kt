package com.example.kotlin.dto

data class GenderResponseDto(
    val count : Int,
    val name: String,
    val gender: String,
    val probability: Float
)