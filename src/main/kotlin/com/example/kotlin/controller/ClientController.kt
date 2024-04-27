package com.example.kotlin.controller

import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/clients")
interface ClientController {

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: Long): ClientDto

    @GetMapping
    fun getClients(): List<ClientDto>

    @PostMapping
    fun addClient(@RequestBody clientDto: ClientSaveDto): ClientDto

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long)
}