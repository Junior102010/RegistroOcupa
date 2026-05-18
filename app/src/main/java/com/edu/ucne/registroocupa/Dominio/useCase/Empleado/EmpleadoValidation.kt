package com.edu.ucne.registroocupa.Dominio.useCase.Empleado

import com.edu.ucne.registroocupa.Dominio.Models.Empleado
import java.time.LocalDate

data class EmpleadoValidation(

    val isValid : Boolean,
    val error : String ? = null

)


fun validateNombres(nombres : String) : EmpleadoValidation
{
    return when{
        nombres.isBlank() -> EmpleadoValidation(false, "La Nombres NO DEBE ESTAR VACIA 😡")
        nombres.length < 3 -> EmpleadoValidation(false, "La Nombres DEBE TENER AL MENOS 3 CARACTERES")
        else -> EmpleadoValidation(true)
    }
}

fun validateSueldo(sueldo : String) : EmpleadoValidation
{
    return when{
        sueldo.isBlank() -> EmpleadoValidation(false, "El Sueldo NO DEBE ESTAR VACIO 😡")
        sueldo.toDoubleOrNull() == null -> EmpleadoValidation(false, "Ingrese un Sueldo Valido!")
        sueldo.toDouble() <= 0.0 -> EmpleadoValidation(false, "El Sueldo debe ser Mayor a 0.0")
        else -> EmpleadoValidation(true)
    }
}

fun validateSexo(sexo : String) : EmpleadoValidation
{
    return when{
        sexo.isBlank() -> EmpleadoValidation(false, "La Sexo NO DEBE ESTAR VACIO 😡, Vamos no seas Timido")
        sexo.length < 1 -> EmpleadoValidation(false, "La Sexo DEBE TENER AL MENOS 1 CARACTERES")
        else -> EmpleadoValidation(true)
    }
}

fun validateFecha(fecha : LocalDate) : EmpleadoValidation
{
    return when{
        fecha.isAfter(LocalDate.now()) -> EmpleadoValidation(false, "La Fecha no puede ser MAYOR A LA ACTUAL")
        else -> EmpleadoValidation(true)
    }
}