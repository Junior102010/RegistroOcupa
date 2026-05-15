package com.edu.ucne.registroocupa.data.Mapped

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import com.edu.ucne.registroocupa.data.local.EmpleadoEntity

fun EmpleadoEntity.toDomain() : Empleado = Empleado(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)

fun Empleado.toEntity() : EmpleadoEntity = EmpleadoEntity(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)