package com.example.kotlin.service.impl

import com.example.kotlin.dto.user.UserRegistrationDto
import com.example.kotlin.dto.user.UserResponseDto
import com.example.kotlin.exeption.BadRequestException
import com.example.kotlin.model.Role
import com.example.kotlin.model.User
import com.example.kotlin.model.toDto
import com.example.kotlin.repository.RoleRepository
import com.example.kotlin.repository.UserRepository
import com.example.kotlin.service.UserService
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    companion object{
        const val ROLE_USER = "ROLE_USER"
    }

    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun registration(user: UserRegistrationDto): UserResponseDto {
        userRepository.findByEmail(user.email)?.let {
            logger.warn { "Can't register user. User with email ${user.email} is already registered" }
            throw BadRequestException("User with email ${user.email} is already registered")
        }
        val userForSave = User(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            userPassword = passwordEncoder.encode(user.password),
            roles = setOf(getRole(ROLE_USER))
        )
        return userRepository.save(userForSave).toDto()

    }

    private fun getRole(name: String): Role {
        return roleRepository.findByName(name) ?: let{
            logger.warn { "Can't register user. No role $name" }
            throw BadRequestException("No such role")
        }
    }
}