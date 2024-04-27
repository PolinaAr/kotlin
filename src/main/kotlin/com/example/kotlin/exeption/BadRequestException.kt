package com.example.kotlin.exeption

import java.lang.RuntimeException

class BadRequestException(message: String) : RuntimeException(message) {
}