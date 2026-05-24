package com.edu.ucne.registroocupa.data.Mapped

import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.data.local.Empleado.EmpleadoEntity

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