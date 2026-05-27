package com.edu.ucne.registroocupa.Presentation.HoraExtra.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.ObserveEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.DeleteHoraExtraUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.HoraExtra.ObserveHoraExtraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HoraExtraListViewModel @Inject constructor(
    private val observeHoraExtraUseCase: ObserveHoraExtraUseCase,
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val deleteHoraExtraUseCase: DeleteHoraExtraUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HoraExtraListUiState(isLoading = true))
    val state: StateFlow<HoraExtraListUiState> = _state.asStateFlow()

    init {
        loadHoraExtra()
    }

    fun onEvent(event: HoraExtraListUiEvent) {
        when (event) {
            HoraExtraListUiEvent.Load -> loadHoraExtra()
            HoraExtraListUiEvent.Refresh -> loadHoraExtra()
            is HoraExtraListUiEvent.Delete -> onDelete(event.id)
            is HoraExtraListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            HoraExtraListUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            HoraExtraListUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is HoraExtraListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
    }



    fun loadHoraExtra() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            combine(
                observeHoraExtraUseCase(),
                observeEmpleadoUseCase()
            ) { horaExtras, empleados ->
                Pair(horaExtras, empleados)
            }.collectLatest { (horaExtras, empleados) ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        HoraExtras = horaExtras,
                        Empleados = empleados,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteHoraExtraUseCase(id)
            onEvent(HoraExtraListUiEvent.ShowMessage("Eliminado"))
        }
    }
}