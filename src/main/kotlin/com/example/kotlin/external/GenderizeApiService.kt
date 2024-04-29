package com.example.kotlin.external

import com.example.kotlin.dto.client.GenderResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

interface GenderizeApiService {

    @GetMapping
    fun getGenderProbability(@RequestParam("name") name: String): GenderResponseDto
}