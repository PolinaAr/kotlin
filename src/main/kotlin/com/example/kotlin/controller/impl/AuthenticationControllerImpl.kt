package com.example.kotlin.controller.impl

import com.example.kotlin.controller.AuthenticationController
import com.example.kotlin.dto.user.ResponseToken
import com.example.kotlin.dto.user.UserLoginRequestDto
import com.example.kotlin.dto.user.UserRegistrationDto
import com.example.kotlin.dto.user.UserResponseDto
import com.example.kotlin.security.AuthenticationService
import com.example.kotlin.service.UserService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationControllerImpl(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) : AuthenticationController {

    private val logger = KotlinLogging.logger {}

    override fun registration(user: UserRegistrationDto): UserResponseDto {
        logger.info { "Registration user with email ${user.email}" }
        return userService.registration(user)
    }

    override fun login(userLoginRequestDto: UserLoginRequestDto): ResponseToken {
        logger.info { "Login in system with email ${userLoginRequestDto.email}" }
        return authenticationService.authenticate(userLoginRequestDto)
    }
}