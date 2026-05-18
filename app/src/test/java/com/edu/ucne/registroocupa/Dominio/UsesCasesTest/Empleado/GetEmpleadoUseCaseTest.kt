package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Empleado

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.getEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.getOcupacionUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetEmpleadoUseCaseTest {
    private lateinit var repository: EmpleadoRepository
    private lateinit var useCase: getEmpleadoUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = getEmpleadoUseCase(repository)
    }

    @Test
    fun `returns task when repository finds it`() = runTest {
        val expectedEmpleado = Empleado(1, LocalDate.now(), "Juan","Masculino",10.0)
        coEvery { repository.getEmpleado(1) } returns expectedEmpleado

        val result = useCase(1)

        assertEquals(expectedEmpleado, result)
    }

    @Test
    fun `returns null when repository returns null`() = runTest {
        coEvery { repository.getEmpleado(999) } returns null

        val result = useCase(999)

        assertNull(result)
    }
}
