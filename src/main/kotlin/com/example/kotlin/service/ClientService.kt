package com.example.kotlin.service

import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import com.example.kotlin.dto.ClientUpdateDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ClientService {

    fun getClientById(id: Long): ClientDto

    fun getClients(searchQuery: String?, searchParams: Map<String, String>, pageable: Pageable): Page<ClientDto>

    fun saveClient(clientDto: ClientSaveDto): ClientDto

    fun updateClient(id: Long, clientDto: ClientUpdateDto): ClientDto

    fun deleteClient(id: Long)
}