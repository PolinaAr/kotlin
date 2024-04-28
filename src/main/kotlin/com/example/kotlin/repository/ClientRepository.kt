package com.example.kotlin.repository

import com.example.kotlin.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    fun existsByEmail(email: String): Boolean
}