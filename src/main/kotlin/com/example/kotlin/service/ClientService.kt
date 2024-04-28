package com.example.kotlin.service

import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import com.example.kotlin.dto.ClientUpdateDto

interface ClientService {

    fun getClientById(id: Long): ClientDto

    fun getClients(): List<ClientDto>

    fun saveClient(clientDto: ClientSaveDto): ClientDto

    fun updateClient(id: Long, clientDto: ClientUpdateDto): ClientDto

    fun deleteClient(id: Long)
}