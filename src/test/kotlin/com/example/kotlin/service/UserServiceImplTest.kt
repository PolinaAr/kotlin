package com.example.kotlin.service

import com.example.kotlin.AbstractUnitTests
import com.example.kotlin.dto.user.UserRegistrationDto
import com.example.kotlin.exeption.BadRequestException
import com.example.kotlin.model.Role
import com.example.kotlin.model.User
import com.example.kotlin.repository.RoleRepository
import com.example.kotlin.repository.UserRepository
import com.example.kotlin.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceImplTest : AbstractUnitTests {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var roleRepository: RoleRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserServiceImpl

    private lateinit var userRegistrationDto: UserRegistrationDto
    private lateinit var user: User
    private lateinit var roleUser: Role

    @BeforeEach
    fun setUp() {
        userRegistrationDto = UserRegistrationDto("email@example.com", "First", "Last", "password")
        roleUser = Role(1L, "ROLE_USER")
        user = User(
            1L, "email@example.com", "First", "Last",
            "encodedPassword", false, setOf(roleUser)
        )
    }

    @Test
    fun `Should register user`() {
        whenever(userRepository.findByEmail(anyString())).thenReturn(null)
        whenever(userRepository.save(any())).thenReturn(user)
        whenever(passwordEncoder.encode(anyString())).thenReturn("encodedPassword")
        whenever(roleRepository.findByName("ROLE_USER")).thenReturn(roleUser)


        val result = userService.registration(userRegistrationDto)

        verify(userRepository).save(any())
        assertEquals(user.email, result.email)
    }

    @Test
    fun `Should throw BadRequestException when user already exists for registration`() {
        whenever(userRepository.findByEmail(anyString())).thenReturn(user)

        assertThrows(BadRequestException::class.java) {
            userService.registration(userRegistrationDto)
        }

        verify(userRepository, never()).save(any(User::class.java))
    }

    @Test
    fun `Should throw BadRequestException when role is not found for registration`() {
        whenever(userRepository.findByEmail(anyString())).thenReturn(null)
        whenever(roleRepository.findByName(UserServiceImpl.ROLE_USER)).thenReturn(null)

        assertThrows(BadRequestException::class.java) {
            userService.registration(userRegistrationDto)
        }

        verify(userRepository, never()).save(any(User::class.java))
    }
}