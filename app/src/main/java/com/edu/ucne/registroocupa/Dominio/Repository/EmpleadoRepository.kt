package com.edu.ucne.registroocupa.Dominio.Repository

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import kotlinx.coroutines.flow.Flow

interface EmpleadoRepository {

    fun observeAll() : Flow<List<Empleado>>
    suspend fun getEmpleado(id : Int): Empleado?
    suspend fun upsert(Empleado: Empleado): Int
    suspend fun delete(id: Int)

}