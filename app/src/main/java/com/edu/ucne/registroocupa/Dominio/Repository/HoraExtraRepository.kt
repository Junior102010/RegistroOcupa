package com.edu.ucne.registroocupa.Dominio.Repository

import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra
import kotlinx.coroutines.flow.Flow

interface HoraExtraRepository {

    fun observeHoraExtra() : Flow<List<HoraExtra>>

    suspend fun getHoraExtra(id: Int): HoraExtra?

    suspend fun upsert(horaExtra: HoraExtra): Int

    suspend fun delete(id: Int)
}