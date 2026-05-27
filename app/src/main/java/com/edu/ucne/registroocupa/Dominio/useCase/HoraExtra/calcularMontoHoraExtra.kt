package com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra

import com.edu.ucne.registroocupa.data.local.Empleado.FrecuenciaPago
import com.edu.ucne.registroocupa.data.local.horaExtra.TipoHoraExtra

fun calcularMontoHoraExtra(
    sueldo: Double,
    frecuenciaDePago: FrecuenciaPago,
    tipoHoraExtra: TipoHoraExtra,
    cantidadHoras: Int,
    esPuestoDireccion: Boolean

): Double{
    if (esPuestoDireccion){
        return 0.0
    }

    val salarioDiario = sueldo/frecuenciaDePago.divisor

    val valorHoraOrdinaria = salarioDiario / 8.0
    val montoTotal = valorHoraOrdinaria * tipoHoraExtra.factor * cantidadHoras

    return Math.round(montoTotal * 100) / 100.0
}