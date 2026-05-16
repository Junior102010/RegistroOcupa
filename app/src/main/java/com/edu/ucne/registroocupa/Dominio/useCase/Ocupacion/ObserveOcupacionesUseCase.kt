package com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveOcupacionesUseCase @Inject constructor(
    private val repository : OcupacionRepository

) {
     operator fun invoke() : Flow<List<Ocupacion>> = repository.observeOcupaciones()
}