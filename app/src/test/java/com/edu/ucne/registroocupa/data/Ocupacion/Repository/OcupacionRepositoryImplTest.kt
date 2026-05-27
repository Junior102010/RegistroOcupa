package com.edu.ucne.registroocupa.data.Ocupacion.Repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion.Ocupacion
import com.edu.ucne.registroocupa.data.Repository.OcupacionRepositoryImpl
import com.edu.ucne.registroocupa.data.local.Ocupacion.OcupacionDao
import com.edu.ucne.registroocupa.data.local.Ocupacion.OcupacionEntity
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

@ExperimentalCoroutinesApi
class OcupacionRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: OcupacionRepositoryImpl
    private lateinit var dao: OcupacionDao

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = OcupacionRepositoryImpl(dao)
    }

    @Test
    fun `upsert guarda Ocupacion correctamente`() = runTest {
        // Given
        val ocupacion = Ocupacion(
            ocupacionesId = 0,
            descripcion = "Una Ocupacion",
            sueldo = 3000.0
        )
        val ocupacionSlot = slot<OcupacionEntity>()
        coEvery { dao.upsert(capture(ocupacionSlot)) } just Runs

        // When
        val result = repository.upsert(ocupacion)

        // Then
        assertEquals(0, result)
        coVerify { dao.upsert(any()) }
        assertEquals(ocupacion.descripcion, ocupacionSlot.captured.descripcion)
        assertEquals(ocupacion.sueldo, ocupacionSlot.captured.sueldo, 0.0)
    }

    @Test
    fun `upsert actualiza ocupacion correctamente`() = runTest {
        // Given
        val ocupacion = Ocupacion(ocupacionesId = 1, descripcion = "jose", sueldo = 45.0)
        coEvery { dao.upsert(any()) } just Runs

        // When
        val result = repository.upsert(ocupacion)

        // Then
        assertEquals(1, result)
        coVerify { dao.upsert(any()) }
    }

    @Test
    fun `delete elimina ocupacion correctamente`() = runTest {
        // Given
        val ocupacionesId = 1
        coEvery { dao.deleteById(ocupacionesId) } just Runs

        // When
        repository.delete(ocupacionesId)

        // Then
        coVerify { dao.deleteById(ocupacionesId) }
    }

    @Test
    fun `observeOcupaciones retorna flow de ocupacion`() = runTest {
        // Given
        val entities = listOf(
            OcupacionEntity(1, "jose", sueldo = 3000.0),
            OcupacionEntity(2, "joseluis", sueldo = 3500.0)
        )
        every { dao.observeAll() } returns flowOf(entities)

        // When
        val result = repository.observeOcupaciones().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("jose", result[0].descripcion)
        assertEquals("joseluis", result[1].descripcion)
    }

    @Test
    fun `getOcupacion retorna ocupacion por id`() = runTest {
        // Given
        val entity = OcupacionEntity(1,  "jose", sueldo = 3000.0)

        coEvery { dao.getById(1) } returns entity

        // When
        val result = repository.getOcupacion(1)

        // Then
        assertNotNull(result)
        assertEquals("jose", result?.descripcion)
        assertEquals(3000.0, result?.sueldo)
    }
}
