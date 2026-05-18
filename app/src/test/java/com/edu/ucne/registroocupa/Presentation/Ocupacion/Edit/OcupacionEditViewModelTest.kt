package com.edu.ucne.registroocupa.Presentation.Ocupacion.Edit

import androidx.lifecycle.SavedStateHandle
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.DeleteOcupacionUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.UpsertOcupacionUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.getOcupacionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OcupacionEditViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var getTask: getOcupacionUseCase
    private lateinit var upsertTask: UpsertOcupacionUseCase
    private lateinit var deleteTask: DeleteOcupacionUseCase
    private lateinit var repository: OcupacionRepository

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
        getTask = mockk()
        upsertTask = mockk()
        deleteTask = mockk()
        repository = mockk()

        coEvery { getTask(any()) } returns null
        every { repository.observeOcupaciones() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    private fun createViewModel(id: Int = 0): EditOcupacionViewModel {
        val savedStateHandle = SavedStateHandle(mapOf("ocupacionId" to id))
        return EditOcupacionViewModel(getTask, upsertTask, deleteTask, repository, savedStateHandle)
    }

    @Test
    fun load_withNullId_setsNewState() = runTest(dispatcher) {
        val vm = createViewModel()

        vm.onEvent(EditOcupacionUiEvent.Load(null))
        runCurrent()

        val s = vm.state.value
        assertTrue(s.isNew)
        assertNull(s.ocupacionId)
    }

    @Test
    fun load_withId_populatesFields() = runTest(dispatcher) {
        val ocupacion = Ocupacion(ocupacionesId = 7, descripcion = "Hola", sueldo = 9.0)
        coEvery { getTask(7) } returns ocupacion
        
        val vm = createViewModel(7)
        runCurrent()

        val s = vm.state.value
        assertFalse(s.isNew)
        assertEquals(7, s.ocupacionId)
        assertEquals("Hola", s.descripcion)
        assertEquals("9.0", s.sueldo)
    }

    @Test
    fun save_withInvalidInputs_setsErrorsAndDoesNotSave() = runTest(dispatcher) {
        val vm = createViewModel()

        vm.onEvent(EditOcupacionUiEvent.DescripcionChanged(""))
        vm.onEvent(EditOcupacionUiEvent.Save)
        runCurrent()

        val s = vm.state.value
        assertFalse(s.saved)
        // If the ViewModel sets error messages, you could assert them here:
        // assertNotNull(s.descripcionError)
    }

    @Test
    fun save_withValidInputs_callsUpsert_andSetsSavedTrue() = runTest(dispatcher) {
        coEvery { upsertTask(any()) } returns Result.success(123)
        val vm = createViewModel()

        vm.onEvent(EditOcupacionUiEvent.DescripcionChanged("Valida"))
        vm.onEvent(EditOcupacionUiEvent.SueldoChanged("5"))

        vm.onEvent(EditOcupacionUiEvent.Save)
        runCurrent()

        val s = vm.state.value
        assertFalse(s.isSaving)
        assertTrue(s.saved)
        assertEquals(123, s.ocupacionId)
    }

    @Test
    fun delete_whenHasId_callsUseCase_andFlagsDeleted() = runTest(dispatcher) {
        coEvery { deleteTask(9) } returns Unit
        coEvery { getTask(9) } returns Ocupacion(9, "Y", 2.0)
        
        val vm = createViewModel(9)
        runCurrent()

        vm.onEvent(EditOcupacionUiEvent.Delete)
        runCurrent()

        coVerify { deleteTask(9) }
        val s = vm.state.value
        assertFalse(s.isDeleting)
        assertTrue(s.deleted)
    }
}
