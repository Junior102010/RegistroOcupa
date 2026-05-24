package com.edu.ucne.registroocupa.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edu.ucne.registroocupa.data.local.Converters
import com.edu.ucne.registroocupa.data.local.Empleado.EmpleadoDao
import com.edu.ucne.registroocupa.data.local.Empleado.EmpleadoEntity
import com.edu.ucne.registroocupa.data.local.Ocupacion.OcupacionDao
import com.edu.ucne.registroocupa.data.local.Ocupacion.OcupacionEntity
import com.edu.ucne.registroocupa.data.local.horaExtra.HoraExtraDao
import com.edu.ucne.registroocupa.data.local.horaExtra.HoraExtraEntity

@Database(
    entities = [OcupacionEntity :: class , EmpleadoEntity:: class, HoraExtraEntity :: class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class RegistroDB : RoomDatabase() {
    abstract fun OcupacionDao() : OcupacionDao

    abstract fun EmpleadoDao() : EmpleadoDao

    abstract fun HoraExtraDao() : HoraExtraDao
}
