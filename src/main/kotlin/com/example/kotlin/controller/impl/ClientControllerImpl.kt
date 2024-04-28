package com.example.kotlin.controller.impl

import com.example.kotlin.controller.ClientController
import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import com.example.kotlin.dto.ClientUpdateDto
import com.example.kotlin.service.ClientService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientControllerImpl(private val clientService: ClientService) : ClientController {

    private val logger = KotlinLogging.logger{}

    override fun getClientById(id: Long): ClientDto {
        logger.info { "Get client with id $id" }
        return clientService.getClientById(id)
    }

    override fun getClients(): List<ClientDto> {
        logger.info { "Get list of clients" }
        return clientService.getClients()
    }

    override fun addClient(clientDto: ClientSaveDto): ClientDto {
        logger.info { "Save client $clientDto" }
        return clientService.saveClient(clientDto)
    }

    override fun updateClient(id: Long, clientDto: ClientUpdateDto): ClientDto {
        logger.info { "Update not ,ull fields client with id $id with data $clientDto" }
        return clientService.updateClient(id, clientDto)
    }

    override fun deleteClient(id: Long) {
        logger.info { "Delete client with id $id" }
        return clientService.deleteClient(id)
    }
}