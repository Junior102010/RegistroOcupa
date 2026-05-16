package com.edu.ucne.registroocupa.data.Repository

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.data.Mapped.toDomain
import com.edu.ucne.registroocupa.data.Mapped.toEntity
import com.edu.ucne.registroocupa.data.local.EmpleadoDao
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class EmpleadoRepositoryImpl @Inject constructor(private val localDataSource: EmpleadoDao):
    EmpleadoRepository
{
    override fun observeAll(): Flow<List<Empleado>> {
        return localDataSource.observeAll().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun getEmpleado(id: Int): Empleado? {
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun upsert(Empleado: Empleado): Int {
        localDataSource.upsert(Empleado.toEntity())
        return Empleado.empleadoId ?: 0
    }

}