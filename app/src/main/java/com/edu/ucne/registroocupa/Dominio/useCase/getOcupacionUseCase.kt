package com.edu.ucne.registroocupa.Dominio.useCase

import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import jakarta.inject.Inject

class getOcupacionUseCase @Inject constructor(
    private val repository : OcupacionRepository
) {
    suspend operator fun invoke(Id : Int) = repository.getOcupacion(Id)
}