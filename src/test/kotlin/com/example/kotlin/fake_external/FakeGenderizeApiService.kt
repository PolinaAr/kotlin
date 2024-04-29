package com.example.kotlin.fake_external

import com.example.kotlin.external.GenderizeApiService
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.RestController

@Profile("test")
@FeignClient(name = "genderize", url = "http://localhost:9999")
interface FakeGenderizeApiService : GenderizeApiService {
}