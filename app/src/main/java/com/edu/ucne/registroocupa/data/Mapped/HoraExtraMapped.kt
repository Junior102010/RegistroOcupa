package com.edu.ucne.registroocupa.data.Mapped

import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra
import com.edu.ucne.registroocupa.data.local.horaExtra.HoraExtraEntity

fun HoraExtraEntity.toDomain() : HoraExtra = HoraExtra(
    horaExtraId = horaExtraId,
    fechaHoras = fechaHoras,
    tipoHoraExtra = tipoHoraExtra,
    cantidadHoraExtra = cantidadHoraExtra,
    recargo = recargo,
    empleadoId = empleadoId,
    esPuestoEjecutivo = esPuestoEjecutivo,
)


fun HoraExtra.toEntity() : HoraExtraEntity = HoraExtraEntity(
    horaExtraId = horaExtraId,
    fechaHoras = fechaHoras,
    tipoHoraExtra = tipoHoraExtra,
    cantidadHoraExtra = cantidadHoraExtra,
    recargo = recargo,
    empleadoId = empleadoId,
    esPuestoEjecutivo = esPuestoEjecutivo,
)