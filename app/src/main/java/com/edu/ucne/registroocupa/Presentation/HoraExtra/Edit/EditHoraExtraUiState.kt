package com.edu.ucne.registroocupa.Presentation.HoraExtra.Edit

import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion.Ocupacion
import com.edu.ucne.registroocupa.data.local.horaExtra.TipoHoraExtra
import java.time.LocalDate

data class EditHoraExtraUiState(
    val horaExtraId: Int? = null,
    val empleadoId: Int = 0,
    val fechaHoras: LocalDate = LocalDate.now(),
    val cantidadHoraExtra: Int = 0,
    val tipoHoraExtra: TipoHoraExtra = TipoHoraExtra.DIURNA,
    val recargo: Double = 0.0,
    val empleados: List<Empleado> = emptyList(),
    val ocupaciones: List<Ocupacion> = emptyList(),
    val esPuestoEjecutivo: Boolean = false,
    val empleadoIdError: String? = null,
    val fechaHorasError: String? = null,
    val cantidadHoraExtraError: String? = null,
    val tipoHoraExtraError: String? = null,
    val recargoError: String? = null,
    val esPuestoEjecutivoError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)
