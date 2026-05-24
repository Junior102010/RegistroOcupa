package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

import com.edu.ucne.registroocupa.data.local.Empleado.FrecuenciaPago
import java.time.LocalDate

sealed interface EditEmpleadoUiEvent {
    data class Load(val id: Int?) : EditEmpleadoUiEvent
    data class NombresChanged(val value: String) : EditEmpleadoUiEvent
    data class SexoChanged(val value: String) : EditEmpleadoUiEvent

    data class FechaIngresoChanged(val value: LocalDate) : EditEmpleadoUiEvent
    data class SueldoChanged(val value: String) : EditEmpleadoUiEvent

    data class FrecuenciaPagoChanged(val value: FrecuenciaPago) : EditEmpleadoUiEvent

    data class OcupacionIdChanged(val value: Int) : EditEmpleadoUiEvent
    
    data object Save : EditEmpleadoUiEvent
    data object Delete : EditEmpleadoUiEvent
}