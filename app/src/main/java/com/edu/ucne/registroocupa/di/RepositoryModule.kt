package com.edu.ucne.registroocupa.di


import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.data.Repository.OcupacionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindOcupacionRepository(impl: OcupacionRepositoryImpl): OcupacionRepository
}