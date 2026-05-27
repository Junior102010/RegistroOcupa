package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Ocupacion

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.GetOcupacionUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetOcupacionUseCaseTest {
    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: GetOcupacionUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetOcupacionUseCase(repository)
    }

    @Test
    fun `returns task when repository finds it`() = runTest {
        val expectedOcupacion = Ocupacion(1, "Desc", 10.0)
        coEvery { repository.getOcupacion(1) } returns expectedOcupacion

        val result = useCase(1)

        assertEquals(expectedOcupacion, result)
    }

    @Test
    fun `returns null when repository returns null`() = runTest {
        coEvery { repository.getOcupacion(999) } returns null

        val result = useCase(999)

        assertNull(result)
    }
}
