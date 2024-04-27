package com.example.kotlin.model

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Version
    var version: Int? = null,
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var created: LocalDateTime = LocalDateTime.now(),
    @UpdateTimestamp
    @Column(nullable = false)
    var updated: LocalDateTime? = LocalDateTime.now()
)