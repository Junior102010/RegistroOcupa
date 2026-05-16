package com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion

data class OcupacionValidation(

    val isValid : Boolean,
    val error : String ? = null

)


fun validateDescription(descripcion : String, ocupacionesExistentes : List<String>) : OcupacionValidation
{
    return when{
        descripcion.isBlank() -> OcupacionValidation(false, "La Descripcion NO DEBE ESTAR VACIA 😡")
        descripcion.length < 3 -> OcupacionValidation(false, "La Descripcion DEBE TENER AL MENOS 3 CARACTERES")
        ocupacionesExistentes.any{ it.equals(descripcion.trim(), ignoreCase = true)} -> OcupacionValidation(false, "Ya Hiciste una Ocupacion con esa Descripcion")
        else -> OcupacionValidation(true)
    }
}

fun validateSueldo(sueldo : String) : OcupacionValidation
{
    return when{
        sueldo.isBlank() -> OcupacionValidation(false, "El Sueldo NO DEBE ESTAR VACIO 😡")
        sueldo.toDoubleOrNull() == null -> OcupacionValidation(false, "Ingrese un Sueldo Valido!")
        sueldo.toDouble() <= 0.0 -> OcupacionValidation(false, "El Sueldo debe ser Mayor a 0.0")
        else -> OcupacionValidation(true)
    }
}