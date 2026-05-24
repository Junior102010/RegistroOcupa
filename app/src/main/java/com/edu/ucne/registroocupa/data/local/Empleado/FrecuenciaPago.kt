package com.edu.ucne.registroocupa.data.local.Empleado

enum class FrecuenciaPago(val mensaje: String, divisor: Double){
    MENSUAL("Mensual", 23.83),
    QUINCENAL("Quincenal", 11.91),
    SEMANAL("Semanal", 5.5),
}