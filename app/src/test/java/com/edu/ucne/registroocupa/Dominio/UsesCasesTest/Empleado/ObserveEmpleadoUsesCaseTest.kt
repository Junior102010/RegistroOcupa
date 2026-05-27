package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Empleado

import app.cash.turbine.test
import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.ObserveEmpleadoUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class ObserveEmpleadoUsesCaseTest {
    private lateinit var repository: EmpleadoRepository
    private lateinit var useCase: ObserveEmpleadoUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveEmpleadoUseCase(repository)
    }

    @Test
    fun `emits lists from repository`() = runTest {
        val shared = MutableSharedFlow<List<Empleado>>()
        every { repository.observeAll() } returns shared

        useCase().test {
            val list1 = listOf(Empleado(1, LocalDate.now(), "Juan", "Masculino"))
            shared.emit(list1)
            assertEquals(list1, awaitItem())

            val list2 = listOf(Empleado(2, LocalDate.now(), "Juanluis", "Masculino"))
            shared.emit(list2)
            assertEquals(list2, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
