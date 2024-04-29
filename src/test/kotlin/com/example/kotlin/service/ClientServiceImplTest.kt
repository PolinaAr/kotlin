package com.example.kotlin.service

import com.example.kotlin.AbstractUnitTests
import com.example.kotlin.dto.client.ClientDto
import com.example.kotlin.dto.client.ClientSaveDto
import com.example.kotlin.dto.client.ClientUpdateDto
import com.example.kotlin.dto.client.GenderResponseDto
import com.example.kotlin.enums.Gender
import com.example.kotlin.exeption.BadRequestException
import com.example.kotlin.external.GenderizeApiService
import com.example.kotlin.model.Client
import com.example.kotlin.model.Job
import com.example.kotlin.model.Position
import com.example.kotlin.repository.ClientRepository
import com.example.kotlin.repository.JobRepository
import com.example.kotlin.repository.PositionRepository
import com.example.kotlin.service.impl.ClientServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*

class ClientServiceImplTest : AbstractUnitTests {

    @Mock
    lateinit var clientRepository: ClientRepository

    @Mock
    lateinit var jobRepository: JobRepository

    @Mock
    lateinit var positionRepository: PositionRepository

    @Mock
    lateinit var genderizeApiService: GenderizeApiService

    @InjectMocks
    lateinit var clientService: ClientServiceImpl

    private val id = 1L
    private lateinit var client: Client
    private lateinit var clientDto: ClientDto
    private lateinit var clientSaveDto: ClientSaveDto
    private lateinit var clientUpdateDto: ClientUpdateDto

    @BeforeEach
    fun setup() {
        client = Client(
            id, "First", "Last",
            "email@example.com", Gender.FEMALE, null, null
        )

        clientDto = ClientDto(
            id, "First", "Last",
            "email@example.com", Gender.FEMALE, null, null
        )

        clientSaveDto = ClientSaveDto(
            "First", "Last",
            "email@example.com", Gender.FEMALE, "Andersen", "Engineer"
        )

        clientUpdateDto = ClientUpdateDto(
            firstName = "FirstNew",
            email = "emailNew@example.com"
        )
    }

    @Test
    fun `Get client by id`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.of(client))

        val result = clientService.getClientById(id)

        assertEquals(clientDto, result)
    }

    @Test
    fun `Throw NoSuchElementException when no client for get client by id`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            clientService.getClientById(id)
        }
    }

    @Test
    fun `Get list of clients`() {
        val page = PageImpl(listOf(client))
        whenever(clientRepository.findAll(Specification.where(null), Pageable.unpaged())).thenReturn(page)

        val result = clientService.getClients(null, emptyMap(), Pageable.unpaged())

        val expected = PageImpl(listOf(clientDto))
        assertEquals(expected, result)
    }

    @Test
    fun `Get empty list of clients`() {
        whenever(clientRepository.findAll(Specification.where(null), Pageable.unpaged())).thenReturn(Page.empty())

        val result = clientService.getClients(null, emptyMap(), Pageable.unpaged())

        assertTrue(result.isEmpty())
    }

    @Test
    fun `Save client`() {
        client.job = Job(1L, "Andersen")
        client.position = Position(1L, "Engineer")

        whenever(clientRepository.existsByEmail(anyString())).thenReturn(false)
        whenever(jobRepository.findByName(anyString())).thenReturn(null)
        whenever(positionRepository.findByName(anyString())).thenReturn(null)
        whenever(jobRepository.save(any())).thenReturn(Job(1L, "Engineer"))
        whenever(positionRepository.save(any())).thenReturn(Position(1L, "Senior"))
        whenever(clientRepository.save(any())).thenReturn(client)

        val result = clientService.saveClient(clientSaveDto)

        assertEquals(clientSaveDto.firstName, result.firstName)
        assertEquals(clientSaveDto.lastName, result.lastName)
        assertEquals(clientSaveDto.email, result.email)
        assertEquals(clientSaveDto.job, result.job)
        assertEquals(clientSaveDto.position, result.position)
    }

    @Test
    fun `Save client when no gender`() {
        clientSaveDto.gender = null
        client.job = Job(1L, "Andersen")
        client.position = Position(1L, "Engineer")
        val genderResponseDto = GenderResponseDto(12345, "First", "female", 0.95f)


        whenever(clientRepository.existsByEmail(anyString())).thenReturn(false)
        whenever(jobRepository.findByName(anyString())).thenReturn(null)
        whenever(positionRepository.findByName(anyString())).thenReturn(null)
        whenever(jobRepository.save(any())).thenReturn(Job(1L, "Engineer"))
        whenever(positionRepository.save(any())).thenReturn(Position(1L, "Senior"))
        whenever(clientRepository.save(any())).thenReturn(client)
        whenever(genderizeApiService.getGenderProbability(anyString())).thenReturn(genderResponseDto)

        val result = clientService.saveClient(clientSaveDto)

        assertEquals(clientSaveDto.firstName, result.firstName)
        assertEquals(clientSaveDto.lastName, result.lastName)
        assertEquals(clientSaveDto.email, result.email)
        assertEquals(clientSaveDto.job, result.job)
        assertEquals(clientSaveDto.position, result.position)
        assertEquals(Gender.FEMALE, result.gender)
    }

    @Test
    fun `Throw BadRequestException when gender not detected for save client`() {
        clientSaveDto.gender = null
        client.job = Job(1L, "Andersen")
        client.position = Position(1L, "Engineer")
        val genderResponseDto = GenderResponseDto(12345, "First", "female", 0.75f)

        whenever(clientRepository.existsByEmail(anyString())).thenReturn(false)
        whenever(jobRepository.findByName(anyString())).thenReturn(null)
        whenever(positionRepository.findByName(anyString())).thenReturn(null)
        whenever(jobRepository.save(any())).thenReturn(Job(1L, "Engineer"))
        whenever(positionRepository.save(any())).thenReturn(Position(1L, "Senior"))
        whenever(genderizeApiService.getGenderProbability(anyString())).thenReturn(genderResponseDto)


        assertThrows(BadRequestException::class.java) {
            clientService.saveClient(clientSaveDto)
        }
    }

    @Test
    fun `Throw BadRequestException when email exist for saving client`() {

        whenever(clientRepository.existsByEmail(clientSaveDto.email)).thenReturn(true)

        assertThrows(BadRequestException::class.java) {
            clientService.saveClient(clientSaveDto)
        }
    }

    @Test
    fun `Update client`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.of(client))

        val updatedClient = clientService.updateClient(id, clientUpdateDto)

        assertEquals(id, updatedClient.id)
        assertEquals(clientUpdateDto.firstName, updatedClient.firstName)
        assertEquals(client.lastName, updatedClient.lastName)
        assertEquals(clientUpdateDto.email, updatedClient.email)
        assertEquals(client.gender, updatedClient.gender)
        assertEquals(client.job?.name, updatedClient.job)
        assertEquals(client.position?.name, updatedClient.position)
    }

    @Test
    fun `Throw NoSuchElementException when no clients for update`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            clientService.updateClient(id, clientUpdateDto)
        }
    }

    @Test
    fun `Throw BadRequestException when email exists for update`() {
        whenever(clientRepository.findById(1L)).thenReturn(Optional.of(client))
        whenever(clientRepository.existsByEmail("emailNew@example.com")).thenReturn(true)

        assertThrows(BadRequestException::class.java) {
            clientService.updateClient(1L, clientUpdateDto)
        }
    }

    @Test
    fun `Delete client by id`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.of(client))

        clientService.deleteClient(id)

        assertTrue(client.deleted)
    }

    @Test
    fun `Throw NoSuchElementException when no client for delete`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            clientService.deleteClient(id)
        }
    }
}