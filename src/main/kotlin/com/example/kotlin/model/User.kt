package com.example.kotlin.model

import com.example.kotlin.dto.user.UserResponseDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "\"user\"")
@SQLRestriction("deleted = false")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var email: String,
    @Column(name = "first_name", nullable = false)
    var firstName: String,
    @Column(name = "last_name", nullable = false)
    var lastName: String,
    @Column(name = "password", nullable = false)
    var userPassword: String,
    var deleted: Boolean = false,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role> = HashSet()
) : UserDetails, BaseEntity() {

    override fun getAuthorities() = roles

    override fun getPassword() = userPassword

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = !deleted
}

fun User.toDto(): UserResponseDto = UserResponseDto(
    id = this.id,
    email = this.email,
    firstName = this.firstName,
    lastName = this.lastName
)