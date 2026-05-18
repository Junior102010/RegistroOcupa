package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

import androidx.lifecycle.SavedStateHandle
import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.DeleteEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.UpsertEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.getEmpleadoUseCase
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class EmpleadoEditViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var getTask: getEmpleadoUseCase
    private lateinit var upsertTask: UpsertEmpleadoUseCase
    private lateinit var deleteTask: DeleteEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setUp() {
        kotlinx.coroutines.Dispatchers.setMain(dispatcher)
        getTask = mockk()
        upsertTask = mockk()
        deleteTask = mockk()
        repository = mockk()

        coEvery { getTask(any()) } returns null
        every { repository.observeAll() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    private fun createViewModel(id: Int = 0): EditEmpleadoViewModel {
        val savedStateHandle = SavedStateHandle(mapOf("empleadoId" to id))
        return EditEmpleadoViewModel(getTask, upsertTask, deleteTask, repository, savedStateHandle)
    }

    @Test
    fun load_withNullId_setsNewState() = runTest(dispatcher) {
        val vm = createViewModel()

        vm.onEvent(EditEmpleadoUiEvent.Load(null))
        runCurrent()

        val s = vm.state.value
        assertTrue(s.isNew)
        assertNull(s.empleadoId)
    }

    @Test
    fun load_withId_populatesFields() = runTest(dispatcher) {
        val empleado = Empleado(empleadoId = 7, nombres = "juan", sueldo = 9.0, fechaIngreso = LocalDate.now(), sexo = "Femenino")
        coEvery { getTask(7) } returns empleado

        val vm = createViewModel(7)
        runCurrent()

        val s = vm.state.value
        assertFalse(s.isNew)
        assertEquals(7, s.empleadoId)
        assertEquals("juan", s.nombres)
        assertEquals("Femenino", s.sexo)
        assertEquals("9.0", s.sueldo)
    }

    @Test
    fun save_withInvalidInputs_setsErrorsAndDoesNotSave() = runTest(dispatcher) {
        val vm = createViewModel()

        vm.onEvent(EditEmpleadoUiEvent.NombresChanged(""))
        vm.onEvent(EditEmpleadoUiEvent.Save)
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

        vm.onEvent(EditEmpleadoUiEvent.NombresChanged("Valida"))
        vm.onEvent(EditEmpleadoUiEvent.SexoChanged("Valida"))
        vm.onEvent(EditEmpleadoUiEvent.FechaIngresoChanged(LocalDate.now()))
        vm.onEvent(EditEmpleadoUiEvent.SueldoChanged("5"))

        vm.onEvent(EditEmpleadoUiEvent.Save)
        runCurrent()

        val s = vm.state.value
        assertFalse(s.isSaving)
        assertTrue(s.saved)
        assertEquals(123, s.empleadoId)
    }

    @Test
    fun delete_whenHasId_callsUseCase_andFlagsDeleted() = runTest(dispatcher) {
        coEvery { deleteTask(9) } returns Unit
        coEvery { getTask(9) } returns Empleado(9, LocalDate.now(), "Y","Femenino", 2.0)

        val vm = createViewModel(9)
        runCurrent()

        vm.onEvent(EditEmpleadoUiEvent.Delete)
        runCurrent()

        coVerify { deleteTask(9) }
        val s = vm.state.value
        assertFalse(s.isDeleting)
        assertTrue(s.deleted)
    }
}
