package com.edu.ucne.registroocupa.Presentation.Empleado.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion.Ocupacion

@Composable
fun EmpleadoListScreen(
    viewModel: EmpleadoListViewModel = hiltViewModel(),
    onAddEmpleado: () -> Unit,
    onEditEmpleado: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    EmpleadoListBody(
        state = state,
        onEvent = viewModel::onEvent,
        onAddClick = onAddEmpleado,
        onEditClick = onEditEmpleado
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoListBody(
    state: EmpleadoListUiState,
    onEvent: (EmpleadoListUiEvent) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Int) -> Unit

) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(EmpleadoListUiEvent.ClearMessage)
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Empleado!"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else {
                if (state.Empleados.isEmpty()) {
                    Text(
                        text = "No hay Empleados",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().testTag("Empleados_list"),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.Empleados,
                            key = { it.empleadoId }
                        ) { Empleado ->
                            EmpleadoItem(
                                empleado = Empleado,
                                onEdit = {onEditClick(Empleado.empleadoId)},
                                onDelete = {onEvent(EmpleadoListUiEvent.Delete(Empleado.empleadoId))}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmpleadoItem(
    empleado: Empleado,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable{onEdit()}.testTag("Empleado_card_${empleado.empleadoId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = empleado.nombres,
                    style = MaterialTheme.typography.bodyLarge
                )


                Text(
                    text = "Sexo: ${empleado.sexo}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Fecha de Ingreso: ${empleado.fechaIngreso.toString()}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Sueldo: RD$ ${empleado.sueldo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )


            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.testTag("btn_delete_${empleado.empleadoId}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Empleado"
                )
            }
        }
    }
}

