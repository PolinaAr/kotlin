package com.example.kotlin.external

import com.example.kotlin.dto.GenderResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "genderize", url = "https://api.genderize.io")
interface GenderizeApiService {

    @GetMapping
    fun getGenderProbability(@RequestParam("name") name: String): GenderResponseDto
}