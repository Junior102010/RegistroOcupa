package com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class UpsertOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion) : Result<Int>
    {
        val listaActual = repository.observeOcupaciones().first().map { it.descripcion }
        val descriptionResult = validateDescription(ocupacion.descripcion, listaActual)
        if(!descriptionResult.isValid){
            return Result.failure(IllegalArgumentException(descriptionResult.error))
        }

        val sueldoResult = validateSueldo(ocupacion.sueldo.toString())
        if(!sueldoResult.isValid){
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        return runCatching { repository.upsert(ocupacion) }
    }
}