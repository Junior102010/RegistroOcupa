package com.edu.ucne.registroocupa.data.local.horaExtra

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface HoraExtraDao
{
    @Upsert
    suspend fun upsert(entity: HoraExtraEntity)

    @Delete
    suspend fun delete(entity: HoraExtraEntity)

    @Query("Select * From horasExtras Order by horaExtraId")
    fun observeAll() : Flow<List<HoraExtraEntity>>

    @Query("Select * From horasExtras WHERE horaExtraId = :id")
    suspend fun getById( id:Int): HoraExtraEntity?

    @Query("DELETE FROM horasExtras WHERE horaExtraId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM horasExtras WHERE horaExtraId = :id)")
    suspend fun exists(id: Int): Boolean
}