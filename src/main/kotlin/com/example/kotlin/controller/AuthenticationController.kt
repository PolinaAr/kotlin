package com.example.kotlin.controller

import com.example.kotlin.dto.user.ResponseToken
import com.example.kotlin.dto.user.UserLoginRequestDto
import com.example.kotlin.dto.user.UserRegistrationDto
import com.example.kotlin.dto.user.UserResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Authentication")
@RequestMapping("/auth")
interface AuthenticationController {

    @PostMapping("/register")
    @Operation(summary = "Register user in system")
    fun registration(@Valid @RequestBody user: UserRegistrationDto): UserResponseDto

    @PostMapping("/login")
    @Operation(summary = "Login in system", description = "Provide email and password")
    fun login(@RequestBody userLoginRequestDto: UserLoginRequestDto): ResponseToken
}