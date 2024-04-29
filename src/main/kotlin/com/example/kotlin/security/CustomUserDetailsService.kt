package com.example.kotlin.security

import com.example.kotlin.repository.UserRepository
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    private val logger = KotlinLogging.logger {}

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)
            ?: let {
                logger.warn { "User with email $username is not found while login" }
                throw UsernameNotFoundException("User with email $username is not found")
            }
    }
}