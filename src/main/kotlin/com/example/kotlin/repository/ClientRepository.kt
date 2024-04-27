package com.example.kotlin.repository

import com.example.kotlin.model.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, Long> {

    fun existsByEmail(email: String): Boolean
}