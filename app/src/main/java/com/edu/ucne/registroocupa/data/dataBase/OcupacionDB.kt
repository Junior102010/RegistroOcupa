package com.edu.ucne.registroocupa.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edu.ucne.registroocupa.data.local.OcupacionDao
import com.edu.ucne.registroocupa.data.local.OcupacionEntity

@Database(
    entities = [OcupacionEntity :: class],
    version = 1
)

abstract class OcupacionDB : RoomDatabase() {
    abstract fun OcupacionDao() : OcupacionDao
}