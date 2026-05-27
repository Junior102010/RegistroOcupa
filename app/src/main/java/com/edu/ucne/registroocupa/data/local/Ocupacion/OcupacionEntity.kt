package com.edu.ucne.registroocupa.data.local.Ocupacion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionesId : Int = 0,
    val descripcion : String ,
    val sueldo : Double,
    val esPuestoEjecutivo: Boolean = false
)