package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

import com.edu.ucne.registroocupa.data.local.Empleado.FrecuenciaPago
import java.time.LocalDate

data class EditEmpleadoUiState(
    val empleadoId: Int? = null,
    val nombres: String = "",
    val fechaIngreso: LocalDate = LocalDate.now(),
    val sexo: String = "",
    val sueldo: String = "",
    val ocupacionId: Int = 0,
    val frecuenciaPago: FrecuenciaPago = FrecuenciaPago.MENSUAL,
    val nombresError: String? = null,
    val fechaIngresoError: String? = null,
    val sexoError: String? = null,
    val sueldoError: String? = null,
    val ocupacionIdError: String? = null,
    val frecuenciaPagoError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
