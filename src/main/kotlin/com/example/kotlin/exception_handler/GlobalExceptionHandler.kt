package com.example.kotlin.exception_handler

import com.example.kotlin.dto.ErrorResponseDto
import com.example.kotlin.exeption.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception
import java.time.LocalDateTime
import java.util.NoSuchElementException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: BadRequestException): ErrorResponseDto {
        return ErrorResponseDto(
            error = ex.javaClass.simpleName,
            message = ex.message ?: "Bad Request",
            status = HttpStatus.BAD_REQUEST.value(),
            timestamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBadRequestException(ex: NoSuchElementException): ErrorResponseDto {
        return ErrorResponseDto(
            error = ex.javaClass.simpleName,
            message = ex.message ?: "No such element",
            status = HttpStatus.NOT_FOUND.value(),
            timestamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception): ErrorResponseDto {
        return ErrorResponseDto(
            error = ex.javaClass.simpleName,
            message = ex.message ?: "Bad Request",
            status = HttpStatus.BAD_REQUEST.value(),
            timestamp = LocalDateTime.now()
        )
    }
}