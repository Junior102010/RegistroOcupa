package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Ocupacion

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.UpsertOcupacionUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpsertOcupacionUseCaseTest {

    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: UpsertOcupacionUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpsertOcupacionUseCase(repository)
        // Mock default behavior for observeOcupaciones to avoid errors in invoke
        every { repository.observeOcupaciones() } returns flowOf(emptyList())
    }

    @Test
    fun `fails when descripcion is blank`() = runTest {
        val result = useCase(Ocupacion(descripcion = "", sueldo = 5.0))

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("La Descripcion NO DEBE ESTAR VACIA 😡", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fails when descripcion is too short`() = runTest {
        val result = useCase(Ocupacion(descripcion = "ab", sueldo = 5.0))

        assertTrue(result.isFailure)
        assertEquals("La Descripcion DEBE TENER AL MENOS 3 CARACTERES", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fails when sueldo is zero`() = runTest {
        val result = useCase(Ocupacion(descripcion = "Valida", sueldo = 0.0))

        assertTrue(result.isFailure)
        assertEquals("El Sueldo debe ser Mayor a 0.0", result.exceptionOrNull()?.message)
    }

    @Test
    fun `succeeds and returns id when repository upsert works`() = runTest {
        val ocupacion = Ocupacion(ocupacionesId = 7, descripcion = "Valida", sueldo = 3.0)
        coEvery { repository.upsert(any()) } returns 7
        
        val result = useCase(ocupacion)

        assertTrue(result.isSuccess)
        assertEquals(7, result.getOrNull())
    }
}
