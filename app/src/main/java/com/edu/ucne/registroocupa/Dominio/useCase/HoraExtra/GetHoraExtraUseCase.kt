package com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra

import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra
import com.edu.ucne.registroocupa.Dominio.Repository.HoraExtraRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetHoraExtraUseCase @Inject constructor(
    private val repository : HoraExtraRepository

) {
    suspend operator fun invoke(id: Int) = repository.getHoraExtra(id)
}