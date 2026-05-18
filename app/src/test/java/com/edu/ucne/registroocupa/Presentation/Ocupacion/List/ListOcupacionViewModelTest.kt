package com.edu.ucne.registroocupa.Presentation.Ocupacion.List

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.DeleteOcupacionUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.ObserveOcupacionesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListOcupacionViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var observeTasks: ObserveOcupacionesUseCase
    private lateinit var deleteTask: DeleteOcupacionUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        observeTasks = mockk()
        deleteTask = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `delete calls use case and updates state`() = runTest {
        val shared = MutableSharedFlow<List<Ocupacion>>(replay = 1)
        shared.emit(emptyList())
        every { observeTasks() } returns shared
        coEvery { deleteTask(5) } returns Unit

        val vm = OcupacionListViewModel(observeTasks, deleteTask)
        runCurrent()

        vm.onEvent(OcupacionListUiEvent.Delete(5))
        runCurrent()

        coVerify { deleteTask(5) }
        assertEquals("Eliminado", vm.state.value.message)
    }

    @Test
    fun `navigation flags toggle as expected`() = runTest {
        val shared = MutableSharedFlow<List<Ocupacion>>(replay = 1)
        shared.emit(emptyList())
        every { observeTasks() } returns shared
        
        val vm = OcupacionListViewModel(observeTasks, deleteTask)
        runCurrent()

        vm.onEvent(OcupacionListUiEvent.CreateNew)
        assertTrue(vm.state.value.navigateToCreate)

        vm.onEvent(OcupacionListUiEvent.Edit(10))
        assertEquals(10, vm.state.value.navigateToEditId)
    }
}
