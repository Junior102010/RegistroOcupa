package com.edu.ucne.registroocupa.Presentation.Ocupacion.List

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion

data class OcupacionListUiState(
    val isLoading: Boolean = false,
    val ocupaciones: List<Ocupacion> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)