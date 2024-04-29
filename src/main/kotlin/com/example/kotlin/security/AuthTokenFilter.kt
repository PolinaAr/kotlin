package com.example.kotlin.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class AuthTokenFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    companion object {
        private const val HEADER_NAME = "Authorization"
        private const val TOKEN_START = "Bearer "
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getToken(request)
        if (token != null && jwtUtil.isValidToken(token)) {
            val username = jwtUtil.getUsername(token)
            val userDetails = userDetailsService.loadUserByUsername(username)
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HEADER_NAME)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_START)) {
            return bearerToken.substring(TOKEN_START.length)
        }
        return null
    }
}