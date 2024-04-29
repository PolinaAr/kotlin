package com.example.kotlin.repository

import com.example.kotlin.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByName(name: String): Role?
}