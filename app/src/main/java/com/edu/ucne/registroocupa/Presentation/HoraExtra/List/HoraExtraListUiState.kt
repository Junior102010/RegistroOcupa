package com.edu.ucne.registroocupa.Presentation.HoraExtra.List

import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra

data class HoraExtraListUiState(
    val isLoading: Boolean = false,

    val HoraExtras: List<HoraExtra> = emptyList(),
    val Empleados: List<Empleado> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)