package com.example.kotlin.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordValidator::class])
annotation class Password(
    val message: String = "Password must contain at least one digit, one capital letter and be no shorter than 8 characters",

    val groups: Array<KClass<*>> = [],

    val payload: Array<KClass<out Payload>> = []
)
