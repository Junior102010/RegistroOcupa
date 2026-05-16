package com.edu.ucne.registroocupa.Dominio.useCase.Empleado

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.validateDescription
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.validateSueldo
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class UpsertEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    suspend operator fun invoke(empleado: Empleado) : Result<Int>
    {
//        val listaActual = repository.observeAll().first().map { it.nombres }
//        val nombresResult = validateNombres(empleado.nombres, listaActual)
//        if(!nombresResult.isValid){
//            return Result.failure(IllegalArgumentException(nombresResult.error))
//        }

        val sueldoResult = validateSueldo(empleado.sueldo.toString())
        if(!sueldoResult.isValid){
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }

        return runCatching { repository.upsert(empleado) }
    }
}