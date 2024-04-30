package com.example.kotlin.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordValidator : ConstraintValidator<Password, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value == null){
            return false
        }
        return value.any { it.isUpperCase() }
                && value.any { it.isDigit() }
                && value.length >= 8
    }
}