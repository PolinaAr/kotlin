package com.example.kotlin.service

import com.example.kotlin.AbstractUnitTests
import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import com.example.kotlin.exeption.BadRequestException
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
import java.util.*

class ClientServiceImplTest : AbstractUnitTests {

    @Mock
    lateinit var clientRepository: ClientRepository

    @Mock
    lateinit var jobRepository: JobRepository

    @Mock
    lateinit var positionRepository: PositionRepository

    @InjectMocks
    lateinit var clientService: ClientServiceImpl

    val id = 1L
    private lateinit var client: Client
    private lateinit var clientDto: ClientDto
    private lateinit var clientSaveDto: ClientSaveDto

    @BeforeEach
    fun setup() {
        client = Client(id, "First", "Last", "email@example.com", null, null)
        clientDto = ClientDto(id, "First", "Last", "email@example.com", null, null)
        clientSaveDto = ClientSaveDto("First", "Last", "email@example.com", "Andersen", "Engineer")
    }

    @Test
    fun `Get client by id`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.of(client))

        val result = clientService.getClientById(id)

        assertEquals(clientDto, result)
    }

    @Test
    fun `Throw exception for get client by id`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            clientService.getClientById(id)
        }
    }

    @Test
    fun `Get list of clients`() {
        whenever(clientRepository.findAll()).thenReturn(listOf(client))

        val result = clientService.getClients()

        assertEquals(listOf(clientDto), result)
    }

    @Test
    fun `Get empty list of clients`() {
        whenever(clientRepository.findAll()).thenReturn(emptyList())

        val result = clientService.getClients()

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
    fun `Throw exception for saving client`() {

        whenever(clientRepository.existsByEmail(clientSaveDto.email)).thenReturn(true)

        assertThrows(BadRequestException::class.java) {
            clientService.saveClient(clientSaveDto)
        }
    }

    @Test
    fun `Delete client by id`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.of(client))

        clientService.deleteClient(id)

        assertTrue(client.deleted)
    }

    @Test
    fun `Throw exception when there is no client for delete`() {
        whenever(clientRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            clientService.deleteClient(id)
        }
    }
}