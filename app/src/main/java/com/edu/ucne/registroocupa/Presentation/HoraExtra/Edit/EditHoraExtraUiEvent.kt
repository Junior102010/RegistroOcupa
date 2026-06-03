package com.edu.ucne.registroocupa.Presentation.HoraExtra.Edit

import com.edu.ucne.registroocupa.data.local.horaExtra.TipoHoraExtra
import java.time.LocalDate

sealed interface EditHoraExtraUiEvent {
    data class Load(val id: Int) : EditHoraExtraUiEvent
    data class EmpleadoIdChanged(val value: Int) : EditHoraExtraUiEvent
    data class CantidadHoraExtraChanged(val value: Int) : EditHoraExtraUiEvent

    data class FechaHorasChanged(val value: LocalDate) : EditHoraExtraUiEvent

    data class TipoHoraExtraChanged(val value: TipoHoraExtra) : EditHoraExtraUiEvent

    data class RecargoChanged(val value: Double) : EditHoraExtraUiEvent
    data class EsPuestoEjecutivoChanged(val value: Boolean) : EditHoraExtraUiEvent
    
    data object Save : EditHoraExtraUiEvent
    data object Delete : EditHoraExtraUiEvent


}