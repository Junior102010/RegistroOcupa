package com.edu.ucne.registroocupa.Dominio.useCase.Empleado

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import com.edu.ucne.registroocupa.Dominio.Repository.EmpleadoRepository
import com.edu.ucne.registroocupa.Dominio.Repository.OcupacionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveEmpleadoUseCase @Inject constructor(
    private val repository : EmpleadoRepository

) {
    operator fun invoke() : Flow<List<Empleado>> = repository.observeAll()
}