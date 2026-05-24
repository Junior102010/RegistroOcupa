package com.edu.ucne.registroocupa.Dominio.Models.Empleado

import com.edu.ucne.registroocupa.data.local.Empleado.FrecuenciaPago
import java.time.LocalDate

data class Empleado(
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombres: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0,
    val frecuenciaPago: FrecuenciaPago,
    val ocupacionesId: Int = 0
)