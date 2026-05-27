package com.edu.ucne.registroocupa.Presentation.Empleado.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.DeleteEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.ObserveEmpleadoUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.DeleteOcupacionUseCase
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.ObserveOcupacionesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EmpleadoListViewModel @Inject constructor(
    private val ObserveEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EmpleadoListUiState(isLoading = true))
    val state: StateFlow<EmpleadoListUiState> = _state.asStateFlow()

    init {
        loadEmpleado()
    }

    fun onEvent(event: EmpleadoListUiEvent) {
        when (event) {
            EmpleadoListUiEvent.Load -> loadEmpleado()
            EmpleadoListUiEvent.Refresh -> loadEmpleado()
            is EmpleadoListUiEvent.Delete -> onDelete(event.id)
            is EmpleadoListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            EmpleadoListUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            EmpleadoListUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is EmpleadoListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
    }



    fun loadEmpleado() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            ObserveEmpleadoUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, Empleados = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteEmpleadoUseCase(id)
            onEvent(EmpleadoListUiEvent.ShowMessage("Eliminado"))
        }
    }
}