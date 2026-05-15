package com.edu.ucne.registroocupa.di

import android.content.Context
import androidx.room.Room
import com.edu.ucne.registroocupa.data.dataBase.RegistroDB
import com.edu.ucne.registroocupa.data.local.EmpleadoDao
import com.edu.ucne.registroocupa.data.local.OcupacionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun ProvideOcupacionDatabase(@ApplicationContext context: Context): RegistroDB
    {
        return Room.databaseBuilder(
            context,
            RegistroDB::class.java,
            "Ocupacion.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(database: RegistroDB): OcupacionDao
    {
        return database.OcupacionDao()
    }

    @Provides
    @Singleton
    fun provideEmpleadoDao(database: RegistroDB): EmpleadoDao
    {
        return database.EmpleadoDao()
    }
}
