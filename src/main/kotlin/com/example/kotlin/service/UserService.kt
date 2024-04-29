package com.example.kotlin.service

import com.example.kotlin.dto.user.UserRegistrationDto
import com.example.kotlin.dto.user.UserResponseDto

interface UserService {

    fun registration(user: UserRegistrationDto): UserResponseDto
}