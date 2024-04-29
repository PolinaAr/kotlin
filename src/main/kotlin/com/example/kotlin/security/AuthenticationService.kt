package com.example.kotlin.security

import com.example.kotlin.dto.user.ResponseToken
import com.example.kotlin.dto.user.UserLoginRequestDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {

    fun authenticate(requestDto: UserLoginRequestDto): ResponseToken {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(requestDto.email, requestDto.password)
        )

        val token = jwtUtil.generateToken(authentication.name)
        return ResponseToken(token)
    }
}