package com.example.kotlin.repository

import com.example.kotlin.model.Job
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface JobRepository : CrudRepository<Job, Long> {

    fun findByName(name: String): Job?
}