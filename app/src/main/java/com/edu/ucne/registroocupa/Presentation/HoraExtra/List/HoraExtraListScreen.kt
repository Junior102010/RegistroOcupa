package com.edu.ucne.registroocupa.Presentation.HoraExtra.List

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
import com.edu.ucne.registroocupa.Dominio.Models.horaExtra.HoraExtra

@Composable
fun HoraExtraListScreen(
    viewModel: HoraExtraListViewModel = hiltViewModel(),
    onAddHoraExtra: () -> Unit,
    onEditHoraExtra: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    HoraExtraListBody(
        state = state,
        onEvent = viewModel::onEvent,
        onAddClick = onAddHoraExtra,
        onEditClick = onEditHoraExtra
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoraExtraListBody(
    state: HoraExtraListUiState,
    onEvent: (HoraExtraListUiEvent) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Int) -> Unit

) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(HoraExtraListUiEvent.ClearMessage)
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
                    contentDescription = "Agregar Horas Extra!"
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
                if (state.HoraExtras.isEmpty()) {
                    Text(
                        text = "No hay Horas Extras",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().testTag("HoraExtra_list"),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.HoraExtras,
                            key = { it.horaExtraId }
                        ) { horaExtra ->
                            val empleado = state.Empleados.find { it.empleadoId == horaExtra.empleadoId }
                            val nombreEmpleado = empleado?.nombres ?: "Empleado Deconocido"
                            HoraExtraItem(
                                horaExtra = horaExtra,
                                empleadoNombre = nombreEmpleado,
                                onEdit = {onEditClick(horaExtra.horaExtraId)}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HoraExtraItem(
    horaExtra: HoraExtra,
    onEdit: () -> Unit,
    empleadoNombre : String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable{onEdit()}.testTag("HoraExtra_card_${horaExtra.horaExtraId}")
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
                    text = empleadoNombre,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Tipo de Horas : ${horaExtra.tipoHoraExtra.descripcion}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Cantidad de Horas: ${horaExtra.cantidadHoraExtra}",
                    style = MaterialTheme.typography.bodyLarge
                )



                Text(
                    text = "Total a Pagar: RD$ ${horaExtra.recargo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )


            }
        }
    }
}

