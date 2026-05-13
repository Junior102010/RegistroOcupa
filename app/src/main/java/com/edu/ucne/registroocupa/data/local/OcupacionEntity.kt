package com.edu.ucne.registroocupa.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionesId : Int = 0,
    val descripcion : String ,
    val sueldo : Double
)
