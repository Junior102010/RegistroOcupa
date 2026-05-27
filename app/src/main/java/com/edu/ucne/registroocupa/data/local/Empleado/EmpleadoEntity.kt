package com.edu.ucne.registroocupa.data.local.Empleado

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.edu.ucne.registroocupa.data.local.Ocupacion.OcupacionEntity
import java.time.LocalDate

@Entity(tableName = "Empleados",
    foreignKeys = [
        ForeignKey(
            entity = OcupacionEntity::class,
            parentColumns = ["ocupacionesId"],
            childColumns = ["ocupacionesId"],
            onDelete = ForeignKey.CASCADE
        )
    ],)
data class EmpleadoEntity(
    @PrimaryKey(autoGenerate = true)
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombres: String = "",
    val sexo: String = "",
    val ocupacionesId: Int = 0,
    val frecuenciaPago: FrecuenciaPago,
    val sueldo: Double = 0.0

)