package com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra

import com.edu.ucne.registroocupa.Dominio.Repository.HoraExtraRepository
import jakarta.inject.Inject

class DeleteHoraExtraUseCase @Inject constructor(
    private val repository : HoraExtraRepository

) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}