package com.edu.ucne.registroocupa.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edu.ucne.registroocupa.data.local.Converters
import com.edu.ucne.registroocupa.data.local.EmpleadoDao
import com.edu.ucne.registroocupa.data.local.EmpleadoEntity
import com.edu.ucne.registroocupa.data.local.OcupacionDao
import com.edu.ucne.registroocupa.data.local.OcupacionEntity

@Database(
    entities = [OcupacionEntity :: class , EmpleadoEntity:: class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class RegistroDB : RoomDatabase() {
    abstract fun OcupacionDao() : OcupacionDao

    abstract fun EmpleadoDao() : EmpleadoDao
}
