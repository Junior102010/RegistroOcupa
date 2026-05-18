package com.edu.ucne.registroocupa.Dominio.useCase.Empleado

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import jakarta.inject.Inject

class UpsertEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    suspend operator fun invoke(empleado: Empleado) : Result<Int>
    {
        val nombresResult = validateNombres(empleado.nombres)
        if(!nombresResult.isValid){
            return Result.failure(IllegalArgumentException(nombresResult.error))
        }

        val sueldoResult = validateSueldo(empleado.sueldo.toString())
        if(!sueldoResult.isValid){
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        return runCatching { repository.upsert(empleado) }
    }
}