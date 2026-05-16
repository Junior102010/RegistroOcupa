package com.edu.ucne.registroocupa.Dominio.useCase.Empleado

import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import jakarta.inject.Inject

class getEmpleadoUseCase @Inject constructor(
    private val repository : EmpleadoRepository
) {
    suspend operator fun invoke(Id : Int) = repository.getEmpleado(Id)
}