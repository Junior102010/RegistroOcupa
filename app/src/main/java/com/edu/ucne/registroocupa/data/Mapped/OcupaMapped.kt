package com.edu.ucne.registroocupa.data.Mapped

import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion.Ocupacion
import com.edu.ucne.registroocupa.data.local.Ocupacion.OcupacionEntity

fun OcupacionEntity.toDomain() : Ocupacion = Ocupacion(
    ocupacionesId = ocupacionesId,
    descripcion = descripcion,
    sueldo = sueldo
)

fun Ocupacion.toEntity() : OcupacionEntity = OcupacionEntity(
    ocupacionesId = ocupacionesId,
    descripcion = descripcion,
    sueldo = sueldo,
    esPuestoEjecutivo = esPuestoEjecutivo
)