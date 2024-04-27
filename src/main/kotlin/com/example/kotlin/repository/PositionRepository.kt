package com.example.kotlin.repository

import com.example.kotlin.model.Position
import org.springframework.data.repository.CrudRepository

interface PositionRepository : CrudRepository<Position, Long> {

    fun findByName(name: String): Position?
}