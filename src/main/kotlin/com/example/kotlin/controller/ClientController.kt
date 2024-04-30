package com.example.kotlin.controller

import com.example.kotlin.dto.client.ClientDto
import com.example.kotlin.dto.client.ClientSaveDto
import com.example.kotlin.dto.client.ClientUpdateDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/clients")
@Tag(name = "Clients")
interface ClientController {

    @GetMapping("/{id}")
    @Operation(summary = "Get client by id", description = "Provide id for getting client")
    fun getClientById(@PathVariable id: Long): ClientDto

    @GetMapping
    @Operation(summary = "Get all clients.", description = "You can add any search query or pageable params")
    fun getClients(
        @RequestParam(required = false) searchQuery: String?,
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) job: String?,
        @RequestParam(required = false) position: String?,
        @PageableDefault(page = 0, size = 10, sort = ["id"]) pageable: Pageable
    ): Page<ClientDto>

    @PostMapping
    @Operation(
        summary = "Add client",
        description = "Provide info about client. Email must be unique. Job and position are optional"
    )
    fun addClient(@Valid @RequestBody clientDto: ClientSaveDto): ClientDto

    @PatchMapping("/{id}")
    @Operation(summary = "Update client", description = "Only not null fields will be updated")
    fun updateClient(@PathVariable id: Long, @Valid @RequestBody clientDto: ClientUpdateDto): ClientDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by id", description = "Provide id for deleting client")
    fun deleteClient(@PathVariable id: Long)
}