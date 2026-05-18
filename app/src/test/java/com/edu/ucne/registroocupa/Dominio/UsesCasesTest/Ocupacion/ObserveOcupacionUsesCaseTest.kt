package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Ocupacion

import app.cash.turbine.test
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.ObserveOcupacionesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveOcupacionUsesCaseTest {
    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: ObserveOcupacionesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveOcupacionesUseCase(repository)
    }

    @Test
    fun `emits lists from repository`() = runTest {
        val shared = MutableSharedFlow<List<Ocupacion>>()
        every { repository.observeOcupaciones() } returns shared

        useCase().test {
            val list1 = listOf(Ocupacion(1, "A", 10.0))
            shared.emit(list1)
            assertEquals(list1, awaitItem())

            val list2 = listOf(Ocupacion(2, "B", 2.0), Ocupacion(3, "C", 3.0))
            shared.emit(list2)
            assertEquals(list2, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
