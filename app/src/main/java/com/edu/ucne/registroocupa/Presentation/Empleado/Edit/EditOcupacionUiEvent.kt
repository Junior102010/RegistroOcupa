package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

sealed interface EditOcupacionUiEvent {
    data class Load(val id: Int?) : EditOcupacionUiEvent
    data class DescripcionChanged(val value: String) : EditOcupacionUiEvent
    data class SueldoChanged(val value: String) : EditOcupacionUiEvent
    data object Save : EditOcupacionUiEvent
    data object Delete : EditOcupacionUiEvent
}