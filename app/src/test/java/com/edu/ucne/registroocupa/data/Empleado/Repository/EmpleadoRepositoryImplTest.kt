package com.edu.ucne.registroocupa.data.Empleado.Repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.data.Repository.EmpleadoRepositoryImpl
import com.edu.ucne.registroocupa.data.local.EmpleadoDao
import com.edu.ucne.registroocupa.data.local.EmpleadoEntity
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class EmpleadoRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: EmpleadoRepositoryImpl
    private lateinit var dao: EmpleadoDao

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = EmpleadoRepositoryImpl(dao)
    }

    @Test
    fun `upsert guarda Empleado correctamente`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            fechaIngreso = LocalDate.now(),
            sueldo = 30.0,
            nombres = "Luis Alberto",
            sexo = "Masculino"
        )
        val empleadoSlot = slot<EmpleadoEntity>()
        coEvery { dao.upsert(capture(empleadoSlot)) } just Runs

        // When
        val result = repository.upsert(empleado)

        // Then
        assertEquals(0, result)
        coVerify { dao.upsert(any()) }
        assertEquals(empleado.nombres, empleadoSlot.captured.nombres)
        assertEquals(empleado.sueldo, empleadoSlot.captured.sueldo, 0.0)
    }

    @Test
    fun `upsert actualiza empleado correctamente`() = runTest {
        // Given
        val empleado = Empleado(empleadoId = 1, nombres = "jose", sueldo = 45.0, fechaIngreso = LocalDate.now(), sexo = "masculino")
        coEvery { dao.upsert(any()) } just Runs

        // When
        val result = repository.upsert(empleado)

        // Then
        assertEquals(1, result)
        coVerify { dao.upsert(any()) }
    }

    @Test
    fun `delete elimina empleado correctamente`() = runTest {
        // Given
        val empleadoId = 1
        coEvery { dao.deleteById(empleadoId) } just Runs

        // When
        repository.delete(empleadoId)

        // Then
        coVerify { dao.deleteById(empleadoId) }
    }

    @Test
    fun `observeEmpleados retorna flow de empleado`() = runTest {
        // Given
        val entities = listOf(
            EmpleadoEntity(1, LocalDate.now(), "jose","masculino", sueldo = 3000.0),
            EmpleadoEntity(2, LocalDate.now(), "joseluis","masculino", sueldo = 3300.0)
        )
        every { dao.observeAll() } returns flowOf(entities)

        // When
        val result = repository.observeAll().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("jose", result[0].nombres)
        assertEquals("joseluis", result[1].nombres)
    }

    @Test
    fun `getEmpleado retorna empleado por id`() = runTest {
        // Given
        val entity = EmpleadoEntity(1, LocalDate.now(), "jose","masculino", sueldo = 3000.0)

        coEvery { dao.getById(1) } returns entity

        // When
        val result = repository.getEmpleado(1)

        // Then
        assertNotNull(result)
        assertEquals("jose", result?.nombres)
        assertEquals("masculino", result?.sexo)
    }
}
