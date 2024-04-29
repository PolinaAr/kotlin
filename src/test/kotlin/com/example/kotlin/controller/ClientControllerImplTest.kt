package com.example.kotlin.controller

import com.example.kotlin.AbstractIntegrationTest
import com.example.kotlin.dto.ClientDto
import com.example.kotlin.dto.ClientSaveDto
import com.example.kotlin.dto.ClientUpdateDto
import com.example.kotlin.enums.Gender
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Sql(
    value = ["/db/client-before-test.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class ClientControllerImplTest : AbstractIntegrationTest() {

    val client1 = ClientDto(
        1L, "Mark", "Johnson", "mark@gmail.com",
        Gender.MALE, null, null
    )
    val client2 = ClientDto(
        2L, "Paula", "Watson", "watson@gmail.com",
        Gender.FEMALE, "Andersen", "Engineer"
    )
    val client3 = ClientDto(
        3L, "Marcus", "Torres", "torres@gmail.com",
        Gender.MALE, "Andersen", "Lead"
    )

    @Test
    fun `Should return client by id`() {
        val clientId = 1
        val expected = objectMapper.writeValueAsString(client1)

        mockMvc.perform(get("/clients/{id}", clientId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expected, true))
    }

    @Test
    fun `Should return clients with default pagination`() {
        mockMvc.perform(get("/clients"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()").value(3))
            .andExpect(jsonPath("$.pageable.pageSize").value(10))
            .andExpect(jsonPath("$.pageable.pageNumber").value(0))
    }

    @Test
    fun `Should return clients with filtered by first and last names`() {
        val firstName = "paul"
        val lastName = "son"
        mockMvc.perform(
            get("/clients")
                .param("firstName", firstName)
                .param("lastName", lastName)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()").value(1))
            .andExpect(jsonPath("$.content[0].id").value(client2.id))
            .andExpect(jsonPath("$.content[0].firstName").value(client2.firstName))
            .andExpect(jsonPath("$.content[0].lastName").value(client2.lastName))
    }

    @Test
    fun `Should return clients with filtered by searchQuery`() {
        val searchQuery = "Mar res"
        mockMvc.perform(
            get("/clients")
                .param("searchQuery", searchQuery)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()").value(1))
            .andExpect(jsonPath("$.content[0].id").value(client3.id))
            .andExpect(jsonPath("$.content[0].firstName").value(client3.firstName))
            .andExpect(jsonPath("$.content[0].lastName").value(client3.lastName))
    }

    @Test
    @Sql(value = ["/db/client-before-save-method.sql"])
    fun `Should save client`() {
        val clientSaveDto = ClientSaveDto(
            firstName = "John",
            lastName = "Watson",
            email = "jwatson@gmail.com",
            gender = Gender.MALE,
            job = "Andersen",
            position = "Lead"
        )

        mockMvc.perform(
            post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientSaveDto))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.firstName").value(clientSaveDto.firstName))
            .andExpect(jsonPath("$.lastName").value(clientSaveDto.lastName))
            .andExpect(jsonPath("$.email").value(clientSaveDto.email))
            .andExpect(jsonPath("$.gender").value(clientSaveDto.gender?.name))
            .andExpect(jsonPath("$.job").value(clientSaveDto.job))
            .andExpect(jsonPath("$.position").value(clientSaveDto.position))

    }

    @Test
    fun `Can't save with the same email`() {
        val clientSaveDto = ClientSaveDto(
            firstName = "John",
            lastName = "Watson",
            email = "watson@gmail.com",
            gender = Gender.MALE,
        )

        mockMvc.perform(
            post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientSaveDto))
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `Update client`() {
        val updateDto = ClientUpdateDto(
            firstName = "New",
            lastName = "New",
            email = "new@gmail.com",
            position = "Lead"
        )

        mockMvc.perform(patch("/clients/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.firstName").value(updateDto.firstName))
            .andExpect(jsonPath("$.lastName").value(updateDto.lastName))
            .andExpect(jsonPath("$.email").value(updateDto.email))
            .andExpect(jsonPath("$.job").value(updateDto.job))
            .andExpect(jsonPath("$.position").value(updateDto.position))
    }

    @Test
    fun `Can't update client - client not found`() {
        val updateDto = ClientUpdateDto(
            firstName = "New",
            lastName = "New"
        )

        mockMvc.perform(patch("/clients/{id}", 999)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Can't update client - email exists`() {
        val updateDto = ClientUpdateDto(
            email = "watson@gmail.com"
        )

        mockMvc.perform(patch("/clients/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Client with email watson@gmail.com already exists"))
    }

    @Test
    fun `Delete client`() {
        mockMvc.perform(delete("/clients/{id}", 1))
            .andExpect(status().isOk)
    }

    @Test
    fun `Can't delete client - client not found`() {
        mockMvc.perform(delete("/clients/{id}", 999))
            .andExpect(status().isNotFound)
    }
}