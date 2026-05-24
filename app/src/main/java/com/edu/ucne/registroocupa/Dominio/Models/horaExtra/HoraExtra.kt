package com.edu.ucne.registroocupa.Dominio.Models.horaExtra

import com.edu.ucne.registroocupa.data.local.horaExtra.TipoHoraExtra
import java.time.LocalDate

data class HoraExtra(
    val horaExtraId: Int = 0,
    val empleadoId: Int = 0,
    val fechaHoras: LocalDate = LocalDate.now(),
    val cantidadHoraExtra: Int = 0,
    val tipoHoraExtra: TipoHoraExtra = TipoHoraExtra.DIURNA,
    val recargo: Double = 0.0,
    val esPuestoEjecutivo: Boolean = false
)