package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.DeleteEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.UpsertEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.GetEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateFecha
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateFrecuenciaPago
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateNombres
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateOcupacionesId
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateSexo
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateSueldo
import com.edu.ucne.registroocupa.Presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditEmpleadoViewModel @Inject constructor(
    private val getEmpleadoUseCase: GetEmpleadoUseCase,
    private val upsertEmpleadoUseCase: UpsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase,
    private val repository: EmpleadoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val routeArgs = savedStateHandle.toRoute<Screen.EmpleadoEdit>()
    private val empleadoId: Int = routeArgs.empleadoId

    private val _state = MutableStateFlow(EditEmpleadoUiState())
    val state: StateFlow<EditEmpleadoUiState> = _state.asStateFlow()

    init {
        loadEmpleado(empleadoId)
    }

    fun onEvent(event: EditEmpleadoUiEvent) {
        when (event) {
            is EditEmpleadoUiEvent.Load -> loadEmpleado(event.id)
            is EditEmpleadoUiEvent.NombresChanged -> _state.update {
                it.copy(nombres = event.value, nombresError = null)
            }
            is EditEmpleadoUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value, sueldoError = null)
            }
            is EditEmpleadoUiEvent.FechaIngresoChanged -> _state.update {
                it.copy(fechaIngreso = event.value, fechaIngresoError = null)
            }
            is EditEmpleadoUiEvent.SexoChanged -> _state.update {
                it.copy(sexo = event.value, sexoError = null)
            }
            is EditEmpleadoUiEvent.FrecuenciaPagoChanged -> _state.update {
                it.copy(frecuenciaPago = event.value, frecuenciaPagoError = null)
            }
            is EditEmpleadoUiEvent.OcupacionIdChanged -> _state.update {
                it.copy(ocupacionId = event.value, ocupacionIdError = null)
            }
            EditEmpleadoUiEvent.Save -> onSave()
            EditEmpleadoUiEvent.Delete -> onDelete()
        }
    }

    private fun loadEmpleado(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, empleadoId = null) }
            return
        }

        viewModelScope.launch {
            val empleado = getEmpleadoUseCase(id)
            if (empleado != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadoId,
                        nombres = empleado.nombres,
                        fechaIngreso = empleado.fechaIngreso,
                        sexo = empleado.sexo,
                        sueldo = empleado.sueldo.toString(),
                        frecuenciaPago = empleado.frecuenciaPago,
                        ocupacionId = empleado.ocupacionesId
                    )
                }
            } else {
                _state.update { it.copy(isNew = true, empleadoId = null) }
            }
        }
    }

    private fun onSave() {

        viewModelScope.launch {
            val nombres = state.value.nombres
            val sexo = state.value.sexo
            val fechaIngreso = state.value.fechaIngreso
            val sueldo = state.value.sueldo
            val frecuenciaPago = state.value.frecuenciaPago
            val ocupacionId = state.value.ocupacionId
            val nombresValidation = validateNombres(nombres)
            val sexoValidation = validateSexo(sexo)
            val fechaIngresoValidations = validateFecha(fechaIngreso)
            val sueldoValidation = validateSueldo(sueldo)
            val frecuenciaPagoValidations = validateFrecuenciaPago(frecuenciaPago)
            val ocupacionIdValidations = validateOcupacionesId(ocupacionId)

            if (!nombresValidation.isValid || !sueldoValidation.isValid || !sexoValidation.isValid ||
                !fechaIngresoValidations.isValid || !frecuenciaPagoValidations.isValid ||
                !ocupacionIdValidations.isValid) {
                _state.update {
                    it.copy(
                        nombresError = nombresValidation.error,
                        sueldoError = sueldoValidation.error,
                        sexoError = sexoValidation.error,
                        fechaIngresoError = fechaIngresoValidations.error,
                        frecuenciaPagoError = frecuenciaPagoValidations.error,
                        ocupacionIdError = ocupacionIdValidations.error
                    )
                }
                return@launch
            }


            _state.update { it.copy(isSaving = true) }

            val empleado = Empleado(
                empleadoId = state.value.empleadoId ?: 0,
                nombres = nombres,
                sexo = sexo,
                fechaIngreso = fechaIngreso,
                sueldo = state.value.sueldo.toDouble(),
                frecuenciaPago = frecuenciaPago,
                ocupacionesId = ocupacionId
                
            )

            val result = upsertEmpleadoUseCase(empleado)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        empleadoId = newId,
                        isNew = false
                    )
                }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.empleadoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}