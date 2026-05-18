package com.edu.ucne.registroocupa.Presentation.Empleado.List

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion

data class EmpleadoListUiState(
    val isLoading: Boolean = false,
    val Empleados: List<Empleado> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)