package com.edu.ucne.registroocupa.data.Repository

import com.edu.ucne.registroocupa.Dominio.Repository.HoraExtraRepository
import com.edu.ucne.registroocupa.data.Mapped.toDomain
import com.edu.ucne.registroocupa.data.Mapped.toEntity
import kotlinx.coroutines.flow.map

import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra
import com.edu.ucne.registroocupa.data.local.horaExtra.HoraExtraDao

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class HoraExtraRepositoryImpl @Inject constructor(private val localDataSource: HoraExtraDao):
    HoraExtraRepository
{
    override fun observeHoraExtra(): Flow<List<HoraExtra>> {
        return localDataSource.observeAll().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun getHoraExtra(id: Int): HoraExtra? {
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun upsert(horaExtra: HoraExtra): Int {
        localDataSource.upsert(horaExtra.toEntity())
        return horaExtra.horaExtraId
    }
}
