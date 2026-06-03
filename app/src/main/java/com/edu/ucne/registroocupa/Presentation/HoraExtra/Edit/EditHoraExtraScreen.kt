package com.edu.ucne.registroocupa.Presentation.HoraExtra.Edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edu.ucne.registroocupa.data.local.Empleado.FrecuenciaPago
import com.edu.ucne.registroocupa.data.local.horaExtra.TipoHoraExtra
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.let

@Composable
fun EditHoraExtraScreen(
    horaExtraId : Int,
    viewModel: EditHoraExtraViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(horaExtraId) {
        viewModel.loadHoraExtra(horaExtraId)
    }

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onBack()
        }
    }

    EditHoraExtraBody(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHoraExtraBody(
    state: EditHoraExtraUiState,
    onEvent: (EditHoraExtraUiEvent) -> Unit,
    onBack: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var expandedEmpleado by remember { mutableStateOf(false) }
    var expandedTipoHoraExtra by remember { mutableStateOf(false) }

    val tipoHoraExtra = TipoHoraExtra.entries

    val selectedEmpleado = state.empleados.find { it.empleadoId == state.empleadoId }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.fechaHoras
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                        onEvent(EditHoraExtraUiEvent.FechaHorasChanged(date))
                    }
                    showDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nueva Horas Extra" else "Editar Horas Extras") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            

            OutlinedTextField(
                value = state.fechaHoras.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                onValueChange = { },
                readOnly = true,
                label = { Text("Fecha de las Horas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_fecha_Horas")
                    .clickable { showDatePicker = true },
                isError = state.fechaHorasError != null,
                supportingText = state.fechaHorasError?.let { { Text(it) } },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                    }
                }
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedEmpleado?.nombres ?: "Seleccionar Empleado",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Empleado") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedEmpleado = true },
                    isError = state.empleadoIdError != null,
                    supportingText = state.empleadoIdError?.let { { Text(it) } },
                    trailingIcon = {
                        IconButton(onClick = { expandedEmpleado = true }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Seleccionar Empleado")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedEmpleado,
                    onDismissRequest = { expandedEmpleado = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    state.empleados.forEach { empleado ->
                        DropdownMenuItem(
                            text = { Text(empleado.nombres) },
                            onClick = {
                                onEvent(EditHoraExtraUiEvent.EmpleadoIdChanged(empleado.empleadoId))
                                expandedEmpleado = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.cantidadHoraExtra.toString(),
                onValueChange = { onEvent(EditHoraExtraUiEvent.CantidadHoraExtraChanged(it.toIntOrNull() ?: 0)) },
                label = { Text("Cantidad Horas Extras") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_CantidadHoras"),
                isError = state.cantidadHoraExtraError != null,
                supportingText = state.cantidadHoraExtraError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )



            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.tipoHoraExtra.descripcion,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Tipo de Hora Extra") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedTipoHoraExtra = true },
                    isError = state.tipoHoraExtraError != null,
                    supportingText = state.tipoHoraExtraError?.let { { Text(it) } },
                    trailingIcon = {
                        IconButton(onClick = { expandedTipoHoraExtra = true }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Seleccionar Frecuencia")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedTipoHoraExtra,
                    onDismissRequest = { expandedTipoHoraExtra = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    tipoHoraExtra.forEach { tipoHoraExtra ->
                        DropdownMenuItem(
                            text = { Text(tipoHoraExtra.descripcion) },
                            onClick = {
                                onEvent(EditHoraExtraUiEvent.TipoHoraExtraChanged(tipoHoraExtra))
                                expandedTipoHoraExtra = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Monto a Pagar: RD$ ${state.recargo}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (state.esPuestoEjecutivo) {
                Text(
                    text = "* Este empleado pertenece a un puesto ejecutivo, no devenga horas extras.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!state.isNew) {
                    Button(
                        onClick = { onEvent(EditHoraExtraUiEvent.Delete) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_eliminar"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        enabled = !state.isDeleting
                    ) {
                        if (state.isDeleting) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Icon(Icons.Default.Delete, contentDescription = null)
                            Spacer(Modifier.size(8.dp))
                            Text("Eliminar")
                        }
                    }
                }

                Button(
                    onClick = { onEvent(EditHoraExtraUiEvent.Save) },
                    modifier = Modifier
                        .weight(if (state.isNew) 1f else 2f)
                        .testTag("btn_guardar"),
                    enabled = !state.isSaving
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}
