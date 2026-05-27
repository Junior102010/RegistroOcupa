package com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra

import java.time.LocalDate

data class HoraExtraValidation(

    val isValid : Boolean,
    val error : String ? = null

)


fun validateEmpleadoId(id : Int) : HoraExtraValidation
{
    return when{
        id == null  -> HoraExtraValidation(false, "La Id Empleado NO DEBE ESTAR VACIA 😡")
        id < 1 -> HoraExtraValidation(false, "El Id DEBE SER MAYOR A 0")
        else -> HoraExtraValidation(true)
    }
}

fun validateCantidadHora(cantidadHora : String) : HoraExtraValidation
{
    val horas = cantidadHora.toInt()
    return when{
        horas == null  -> HoraExtraValidation(false, "Las Horas NO DEBEN ESTAR VACIA 😡")
        horas < 1 -> HoraExtraValidation(false, "Las Horas DEBEN SER MAYOR A 0")
        else -> HoraExtraValidation(true)
    }
}


fun validateFechaHora(fecha : LocalDate) : HoraExtraValidation
{
    return when{
        fecha.isAfter(LocalDate.now()) -> HoraExtraValidation(false, "La Fecha no puede ser MAYOR A LA ACTUAL")
        else -> HoraExtraValidation(true)
    }
}

