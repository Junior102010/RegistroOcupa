package com.edu.ucne.registroocupa.data.local.horaExtra

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.edu.ucne.registroocupa.data.local.Empleado.EmpleadoEntity
import java.time.LocalDate

@Entity(tableName = "horasExtras",
   foreignKeys = [
      ForeignKey(
         entity = EmpleadoEntity::class,
         parentColumns = ["empleadoId"],
         childColumns = ["empleadoId"],
         onDelete = ForeignKey.CASCADE
      )
   ],)
data class HoraExtraEntity(
    @PrimaryKey(autoGenerate = true)
   val horaExtraId: Int = 0,
   val empleadoId: Int = 0,
   val fechaHoras: LocalDate = LocalDate.now(),
   val cantidadHoraExtra: Int = 0,
   val tipoHoraExtra: TipoHoraExtra = TipoHoraExtra.DIURNA,
   val recargo: Double = 0.0,
   val esPuestoEjecutivo: Boolean = false
)