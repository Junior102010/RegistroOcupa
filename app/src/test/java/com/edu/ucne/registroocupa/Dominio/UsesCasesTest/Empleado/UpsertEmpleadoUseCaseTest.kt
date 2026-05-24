package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Empleado

import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.UpsertEmpleadoUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpsertEmpleadoUseCaseTest {

    private lateinit var repository: EmpleadoRepository
    private lateinit var useCase: UpsertEmpleadoUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpsertEmpleadoUseCase(repository)
        // Mock default behavior for observeOcupaciones to avoid errors in invoke
        every { repository.observeAll() } returns flowOf(emptyList())
    }

    @Test
    fun `fails when Nombres is blank`() = runTest {
        val result = useCase(Empleado(nombres = "", sueldo = 5.0))

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("La Nombres NO DEBE ESTAR VACIA 😡", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fails when Nombres is too short`() = runTest {
        val result = useCase(Empleado(nombres = "ab", sueldo = 5.0))

        assertTrue(result.isFailure)
        assertEquals("La Nombres DEBE TENER AL MENOS 3 CARACTERES", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fails when sueldo is zero`() = runTest {
        val result = useCase(Empleado(nombres = "Valida", sueldo = 0.0))

        assertTrue(result.isFailure)
        assertEquals("El Sueldo debe ser Mayor a 0.0", result.exceptionOrNull()?.message)
    }

    @Test
    fun `succeeds and returns id when repository upsert works`() = runTest {
        val empleado = Empleado(empleadoId = 7, nombres = "Valida", sueldo = 3.0)
        coEvery { repository.upsert(any()) } returns 7
        
        val result = useCase(empleado)

        assertTrue(result.isSuccess)
        assertEquals(7, result.getOrNull())
    }
}
