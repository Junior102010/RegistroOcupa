package com.edu.ucne.registroocupa.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface OcupacionDao {
    @Upsert
    suspend fun upsert(entity: OcupacionEntity)

    @Delete
    suspend fun delete(entity: OcupacionEntity)

    @Query("Select * From Ocupaciones Order by ocupacionesId")
    fun observeAll() : Flow<List<OcupacionEntity>>

    @Query("Select * From Ocupaciones WHERE ocupacionesId = :id")
    suspend fun getById( id:Int): OcupacionEntity?

    @Query("DELETE FROM Ocupaciones WHERE ocupacionesId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM Ocupaciones WHERE ocupacionesId = :id)")
    suspend fun exists(id: Int): Boolean
}