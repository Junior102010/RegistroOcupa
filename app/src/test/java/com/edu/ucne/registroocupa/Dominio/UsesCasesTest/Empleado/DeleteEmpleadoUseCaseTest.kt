package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Empleado

import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.DeleteEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.DeleteOcupacionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteEmpleadoUseCaseTest {
    private lateinit var repository: EmpleadoRepository
    private lateinit var useCase: DeleteEmpleadoUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = DeleteEmpleadoUseCase(repository)
    }

    @Test
    fun `calls repository delete with id`() = runTest {
        coEvery { repository.delete(5) } just runs

        useCase(5)

        coVerify { repository.delete(5) }
    }
}