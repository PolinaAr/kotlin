package com.example.kotlin.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${app.jwt.secret}") secretString: String) {
    private val secret: SecretKey = Keys.hmacShaKeyFor(secretString.toByteArray(StandardCharsets.UTF_8))

    @Value("\${app.jwt.expirationTime}")
    private var expiration: Long = 0

    fun generateToken(username: String): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secret)
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        return try {
            val claimsJws: Jws<Claims> = Jwts.parser()
                .decryptWith(secret)
                .build()
                .parseSignedClaims(token)

            !claimsJws.payload.expiration.before(Date())
        } catch (e: JwtException) {
            throw JwtException("Token is not correct")
        } catch (e: IllegalArgumentException) {
            throw JwtException("Token is not correct")
        }
    }

    fun getUsername(token: String): String {
        return getClaimFromToken(token, Claims::getSubject)
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = Jwts.parser()
            .decryptWith(secret)
            .build()
            .parseSignedClaims(token)
            .payload
        return claimsResolver.apply(claims)
    }
}