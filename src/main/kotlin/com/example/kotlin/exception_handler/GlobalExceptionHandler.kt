package com.example.kotlin.exception_handler

import com.example.kotlin.dto.ErrorResponseDto
import com.example.kotlin.exeption.BadRequestException
import com.example.kotlin.exeption.UserNotFoundException
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
            message = ex.message ?: BAD_REQUEST,
            status = HttpStatus.BAD_REQUEST.value(),
            timestamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBadRequestException(ex: NoSuchElementException): ErrorResponseDto {
        return ErrorResponseDto(
            error = ex.javaClass.simpleName,
            message = ex.message ?: NO_SUCH_ELEMENT,
            status = HttpStatus.NOT_FOUND.value(),
            timestamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBadRequestException(ex: UserNotFoundException): ErrorResponseDto {
        return ErrorResponseDto(
            error = ex.javaClass.simpleName,
            message = ex.message ?: USER_NOT_FOUND,
            status = HttpStatus.NOT_FOUND.value(),
            timestamp = LocalDateTime.now()
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception): ErrorResponseDto {
        return ErrorResponseDto(
            error = ex.javaClass.simpleName,
            message = ex.message ?: BAD_REQUEST,
            status = HttpStatus.BAD_REQUEST.value(),
            timestamp = LocalDateTime.now()
        )
    }

    companion object{
        const val BAD_REQUEST = "Bad Request"
        const val NO_SUCH_ELEMENT = "No such element"
        const val USER_NOT_FOUND = "User not found"
    }
}