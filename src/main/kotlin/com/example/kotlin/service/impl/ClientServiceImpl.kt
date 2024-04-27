package com.example.kotlin.service.impl

import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import com.example.kotlin.enums.Gender
import com.example.kotlin.exeption.BadRequestException
import com.example.kotlin.external.GenderizeApiService
import com.example.kotlin.model.Client
import com.example.kotlin.model.Job
import com.example.kotlin.model.Position
import com.example.kotlin.model.toDto
import com.example.kotlin.repository.ClientRepository
import com.example.kotlin.repository.JobRepository
import com.example.kotlin.repository.PositionRepository
import com.example.kotlin.service.ClientService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClientServiceImpl(
    private val clientRepository: ClientRepository,
    private val jobRepository: JobRepository,
    private val positionRepository: PositionRepository,
    private val genderizeApiService: GenderizeApiService
) : ClientService {

    private val logger = KotlinLogging.logger {}

    @Transactional(readOnly = true)
    override fun getClientById(id: Long): ClientDto {
        return getClientByIdOrThrow(id).toDto()
    }

    @Transactional(readOnly = true)
    override fun getClients(): List<ClientDto> {
        return clientRepository.findAll().map { client -> client.toDto() }
    }

    @Transactional
    override fun saveClient(clientDto: ClientSaveDto): ClientDto {
        checkUniqueEmail(clientDto.email)

        val job = clientDto.job?.let { jobName ->
            jobRepository.findByName(jobName) ?: jobRepository.save(Job(name = jobName))
        }

        val position = clientDto.position?.let { positionName ->
            positionRepository.findByName(positionName) ?: positionRepository.save(Position(name = positionName))
        }

        val client = Client(
            firstName = clientDto.firstName,
            lastName = clientDto.lastName,
            email = clientDto.email,
            gender = clientDto.gender ?: getGender(clientDto.firstName),
            job = job,
            position = position
        )
        return clientRepository.save(client).toDto()
    }

    @Transactional
    override fun deleteClient(id: Long) {
        val client = getClientByIdOrThrow(id)
        client.deleted = true
    }

    private fun getClientByIdOrThrow(id: Long): Client = clientRepository.findById(id).orElseThrow {
        logger.warn { "Client with id $id not found" }
        NoSuchElementException("Client with id $id not found")
    }

    private fun checkUniqueEmail(email: String) {
        if (clientRepository.existsByEmail(email)) {
            logger.warn { "Client with email $email already exists" }
            throw BadRequestException("Client with email $email already exists")
        }
    }

    private fun getGender(firstName: String): Gender {
        logger.info { "Send request to determine gender for $firstName" }
        val genderResponseDto = genderizeApiService.getGenderProbability(firstName)
        if (genderResponseDto.probability >= 0.8) {
            return Gender.valueOf(genderResponseDto.gender.uppercase())
        } else {
            logger.warn { "Gender is not detected for $firstName. Probability is ${genderResponseDto.probability}" }
            throw BadRequestException("Gender is not detected")
        }
    }
}
