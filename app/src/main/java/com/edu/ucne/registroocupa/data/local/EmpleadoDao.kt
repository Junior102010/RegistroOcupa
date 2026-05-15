package com.edu.ucne.registroocupa.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleadoDao {
    @Upsert
    suspend fun upsert(empleado: EmpleadoEntity)

    @Delete
    suspend fun delete(empleado: EmpleadoEntity)

    @Query("DELETE FROM Empleados WHERE empleadoId = :id")
    suspend fun deleteById(id: Int)


    @Query("SELECT * FROM Empleados WHERE empleadoId = :id")
    suspend fun getById(id: Int): EmpleadoEntity?

    @Query("SELECT * FROM Empleados")
    fun getAll(): Flow<List<EmpleadoEntity>>
}
