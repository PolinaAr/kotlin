package com.example.kotlin.external

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Profile

@Profile("!test")
@FeignClient(name = "genderize", url = "https://api.genderize.io")
interface GenderizeApiServiceDefault : GenderizeApiService