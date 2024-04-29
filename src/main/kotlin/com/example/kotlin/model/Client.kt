package com.example.kotlin.model

import com.example.kotlin.dto.client.ClientDto
import com.example.kotlin.enums.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted = false")
data class Client(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "first_name", nullable = false)
    var firstName: String,
    @Column(name = "last_name", nullable = false)
    var lastName: String,
    @Column(nullable = false)
    var email: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var gender: Gender,
    @ManyToOne
    @JoinColumn(name = "job_id")
    var job: Job? = null,
    @ManyToOne
    @JoinColumn(name = "position_id")
    var position: Position? = null,
    var deleted: Boolean = false
) : BaseEntity()

fun Client.toDto(): ClientDto = ClientDto(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    gender = this.gender,
    job = this.job?.name,
    position = this.position?.name
)