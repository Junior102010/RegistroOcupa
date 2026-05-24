package com.edu.ucne.registroocupa.Dominio.Models.Ocupacion

data class Ocupacion(
    val ocupacionesId : Int = 0,
    val descripcion : String = "",
    val sueldo : Double = 0.0,

    val esPuestoEjecutivo: Boolean = false
)