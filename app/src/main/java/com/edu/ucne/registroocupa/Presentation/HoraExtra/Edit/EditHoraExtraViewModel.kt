package com.edu.ucne.registroocupa.Presentation.HoraExtra.Edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.ObserveEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.DeleteHoraExtraUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.GetHoraExtraUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.ObserveHoraExtraUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.UpsertHoraExtraUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.calcularMontoHoraExtra
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.validateCantidadHora
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.validateEmpleadoId
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.validateFechaHora
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.ObserveOcupacionesUseCase
import com.edu.ucne.registroocupa.Presentation.navigation.Screen
import com.edu.ucne.registroocupa.data.local.Empleado.FrecuenciaPago
import com.edu.ucne.registroocupa.data.local.horaExtra.TipoHoraExtra
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditHoraExtraViewModel @Inject constructor(
    private val getHoraExtraUseCase: GetHoraExtraUseCase,
    private val upsertHoraExtraUseCase: UpsertHoraExtraUseCase,
    private val deleteHoraExtraUseCase: DeleteHoraExtraUseCase,
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val observeOcupacionesUseCase: ObserveOcupacionesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val routeArgs = savedStateHandle.toRoute<Screen.HoraExtraEdit>()
    private val horaExtraId: Int = routeArgs.horaExtraId

    private val _state = MutableStateFlow(EditHoraExtraUiState())
    val state: StateFlow<EditHoraExtraUiState> = _state.asStateFlow()

    init {
        loadHoraExtra(horaExtraId)
        observeEmpleados()
        observeOcupaciones()
    }

    private fun observeEmpleados() {
        viewModelScope.launch {
            observeEmpleadoUseCase().collect { empleados ->
                _state.update { it.copy(empleados = empleados) }
                recalcularTotal()
            }
        }
    }

    private fun observeOcupaciones() {
        viewModelScope.launch {
            observeOcupacionesUseCase().collect { ocupaciones ->
                _state.update { it.copy(ocupaciones = ocupaciones) }
                recalcularTotal()
            }
        }
    }

    fun onEvent(event: EditHoraExtraUiEvent) {
        when (event) {
            is EditHoraExtraUiEvent.Load -> loadHoraExtra(event.id)
            is EditHoraExtraUiEvent.CantidadHoraExtraChanged -> {
                _state.update {
                    it.copy(cantidadHoraExtra = event.value, cantidadHoraExtraError = null)
                }
                recalcularTotal()
            }

            is EditHoraExtraUiEvent.TipoHoraExtraChanged -> {
                _state.update {
                    it.copy(tipoHoraExtra = event.value, tipoHoraExtraError = null)
                }
                recalcularTotal()
            }

            is EditHoraExtraUiEvent.FechaHorasChanged -> _state.update {
                it.copy(fechaHoras = event.value, fechaHorasError = null)

            }

            is EditHoraExtraUiEvent.RecargoChanged -> _state.update {
                it.copy(recargo = event.value, recargoError = null)
            }

            is EditHoraExtraUiEvent.EsPuestoEjecutivoChanged -> _state.update {
                it.copy(esPuestoEjecutivo = event.value, esPuestoEjecutivoError = null)

            }

            is EditHoraExtraUiEvent.EmpleadoIdChanged -> {
                _state.update {
                    it.copy(empleadoId = event.value, empleadoIdError = null)
                }
                recalcularTotal()
            }

            EditHoraExtraUiEvent.Save -> onSave()
            EditHoraExtraUiEvent.Delete -> onDelete()
        }
    }

    private fun loadHoraExtra(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, horaExtraId = null) }

            return
        }

        viewModelScope.launch {
            val horaExtra = getHoraExtraUseCase(id)
            if (horaExtra != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        horaExtraId = horaExtra.horaExtraId,
                        empleadoId = horaExtra.empleadoId,
                        fechaHoras = horaExtra.fechaHoras,
                        cantidadHoraExtra = horaExtra.cantidadHoraExtra,
                        tipoHoraExtra = horaExtra.tipoHoraExtra,
                        recargo = horaExtra.recargo,
                        esPuestoEjecutivo = horaExtra.esPuestoEjecutivo
                    )
                }
                recalcularTotal()
            } else {
                _state.update { it.copy(isNew = true, horaExtraId = null) }
            }
        }
    }

    private fun recalcularTotal() {
        val currentState = _state.value
        val empleado = currentState.empleados.find { it.empleadoId == currentState.empleadoId }
        val ocupacion = currentState.ocupaciones.find { it.ocupacionesId == empleado?.ocupacionesId }

        val sueldo = empleado?.sueldo ?: 0.0
        val frecuencia = empleado?.frecuenciaPago ?: FrecuenciaPago.SEMANAL
        val esDireccion = ocupacion?.esPuestoEjecutivo ?: false
        val horas = currentState.cantidadHoraExtra

        val nuevoRecargo = calcularMontoHoraExtra(
            sueldo = sueldo,
            frecuenciaDePago = frecuencia,
            tipoHoraExtra = currentState.tipoHoraExtra,
            cantidadHoras = horas,
            esPuestoDireccion = esDireccion
        )
        _state.update { it.copy(recargo = nuevoRecargo, esPuestoEjecutivo = esDireccion) }
    }
    private fun onSave() {

        viewModelScope.launch {
            val empleadoId = state.value.empleadoId
            val fechaHoras = state.value.fechaHoras
            val cantidadHoraExtra = state.value.cantidadHoraExtra.toString()
            val recargo = state.value.recargo
            val esPuestoEjecutivo = state.value.esPuestoEjecutivo
            val cantidadHoraValidation = validateCantidadHora(cantidadHoraExtra)
            val fechaHorasPagoValidation = validateFechaHora(fechaHoras)
            val empleadoIdValidation = validateEmpleadoId(empleadoId)

            if (!cantidadHoraValidation.isValid || !fechaHorasPagoValidation.isValid || !empleadoIdValidation.isValid
                ){
                _state.update {
                    it.copy(
                        empleadoIdError = empleadoIdValidation.error,
                        fechaHorasError = fechaHorasPagoValidation.error,
                        cantidadHoraExtraError = cantidadHoraValidation.error,
                    )
                }
                return@launch
            }


            _state.update { it.copy(isSaving = true) }

            val horaExtra = HoraExtra(
                horaExtraId = state.value.horaExtraId ?: 0,
                recargo = recargo,
                tipoHoraExtra = state.value.tipoHoraExtra,
                fechaHoras = fechaHoras,
                cantidadHoraExtra = state.value.cantidadHoraExtra,
                esPuestoEjecutivo = esPuestoEjecutivo,
                empleadoId = empleadoId
            )

            val result = upsertHoraExtraUseCase(horaExtra)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        horaExtraId = newId,
                        isNew = false
                    )
                }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.horaExtraId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteHoraExtraUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}