package com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion

import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import jakarta.inject.Inject

class DeleteOcupacionUseCase @Inject constructor(
    private val repository : OcupacionRepository
) {

    suspend operator fun invoke(Id : Int) = repository.delete(Id)

}