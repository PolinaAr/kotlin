package com.example.kotlin.controller.impl

import com.example.kotlin.controller.ClientController
import com.example.kotlin.dto.client.ClientDto
import com.example.kotlin.dto.client.ClientSaveDto
import com.example.kotlin.dto.client.ClientUpdateDto
import com.example.kotlin.service.ClientService
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientControllerImpl(private val clientService: ClientService) : ClientController {

    private val logger = KotlinLogging.logger {}

    override fun getClientById(id: Long): ClientDto {
        logger.info { "Get client with id $id" }
        return clientService.getClientById(id)
    }

    override fun getClients(
        searchQuery: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        job: String?,
        position: String?,
        pageable: Pageable
    ): Page<ClientDto> {
        val params = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "job" to job,
            "position" to position
        ).filterValues { it != null }.mapValues { it.value!! }
        logger.info {
            "Get list of clients with search params: searchQuery=$searchQuery, " +
                    "pageable=$pageable, params=$params"
        }
        return clientService.getClients(searchQuery, params, pageable)
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