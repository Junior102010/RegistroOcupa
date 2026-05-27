package com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra

import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra
import com.edu.ucne.registroocupa.Dominio.Repository.HoraExtraRepository
import jakarta.inject.Inject

class UpsertHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
) {
    suspend operator fun invoke(horaExtra: HoraExtra) : Result<Int>
    {
        val empleadoIdResult = validateEmpleadoId(horaExtra.empleadoId)
        if(!empleadoIdResult.isValid){
            return Result.failure(IllegalArgumentException(empleadoIdResult.error))
        }

        val cantidadHoraResult = validateCantidadHora(horaExtra.cantidadHoraExtra.toString())
        if(!cantidadHoraResult.isValid){
            return Result.failure(IllegalArgumentException(cantidadHoraResult.error))
        }

        return runCatching { repository.upsert(horaExtra) }
    }
}