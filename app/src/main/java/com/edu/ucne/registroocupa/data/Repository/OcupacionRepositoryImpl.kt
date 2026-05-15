package com.edu.ucne.registroocupa.data.Repository

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.data.Mapped.toDomain
import com.edu.ucne.registroocupa.data.Mapped.toEntity
import com.edu.ucne.registroocupa.data.local.OcupacionDao
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OcupacionRepositoryImpl @Inject constructor(private val localDataSource: OcupacionDao) : OcupacionRepository {
    override fun observeOcupaciones(): Flow<List<Ocupacion>> {
        return localDataSource.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? {
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun upsert(Ocupacion: Ocupacion): Int {
        localDataSource.upsert(Ocupacion.toEntity())
        return Ocupacion.ocupacionesId ?: 0
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
        return localDataSource.exists(id)
    }


}
