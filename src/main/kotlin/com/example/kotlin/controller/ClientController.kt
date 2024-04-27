package com.example.kotlin.controller

import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/clients")
@Tag(name = "Clients")
interface ClientController {

    @GetMapping("/{id}")
    @Operation(summary = "Get client by id", description = "Provide id for getting client")
    fun getClientById(@PathVariable id: Long): ClientDto

    @GetMapping
    @Operation(summary = "Get all clients")
    fun getClients(): List<ClientDto>

    @PostMapping
    @Operation(
        summary = "Add client",
        description = "Provide info about client. Email must be unique. Job and position are optional"
    )
    fun addClient(@RequestBody clientDto: ClientSaveDto): ClientDto

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by id", description = "Provide id for deleting client")
    fun deleteClient(@PathVariable id: Long)
}