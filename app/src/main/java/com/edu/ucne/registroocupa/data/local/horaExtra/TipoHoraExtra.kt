package com.edu.ucne.registroocupa.data.local.horaExtra

enum class TipoHoraExtra(val descripcion: String, val factor: Double){
    DIURNA("Diurna", 1.35),
    NOCTURNA("Nocturna", 1.50),
    DIA_LIBRE_FERIADO("Día libre o feriado", 2.0),
    ALTO_VOLUMEN("Alto volumen", 2.0)
}