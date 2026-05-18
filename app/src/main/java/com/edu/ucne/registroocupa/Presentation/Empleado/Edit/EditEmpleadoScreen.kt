package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EditEmpleadoScreen(
    viewModel: EditEmpleadoViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            onBack()
        }
    }

    EditEmpleadoBody(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmpleadoBody(
    state: EditEmpleadoUiState,
    onEvent: (EditEmpleadoUiEvent) -> Unit,
    onBack: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var expandedSexo by remember { mutableStateOf(false) }
    val sexos = listOf("Masculino", "Femenino", "Otros")

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.fechaIngreso
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
                        onEvent(EditEmpleadoUiEvent.FechaIngresoChanged(date))
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
                title = { Text(if (state.isNew) "Nuevo Empleado" else "Editar Empleado") },
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
                value = state.nombres,
                onValueChange = { onEvent(EditEmpleadoUiEvent.NombresChanged(it)) },
                label = { Text("Nombres") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_nombres"),
                isError = state.nombresError != null,
                supportingText = state.nombresError?.let { { Text(it) } },
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.sexo,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Sexo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_sexo")
                        .clickable { expandedSexo = true },
                    isError = state.sexoError != null,
                    supportingText = state.sexoError?.let { { Text(it) } },
                    trailingIcon = {
                        IconButton(onClick = { expandedSexo = true }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Seleccionar Sexo")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedSexo,
                    onDismissRequest = { expandedSexo = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    sexos.forEach { sexoOption ->
                        DropdownMenuItem(
                            text = { Text(sexoOption) },
                            onClick = {
                                onEvent(EditEmpleadoUiEvent.SexoChanged(sexoOption))
                                expandedSexo = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.fechaIngreso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                onValueChange = { },
                readOnly = true,
                label = { Text("Fecha de Ingreso") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_fecha_ingreso")
                    .clickable { showDatePicker = true },
                isError = state.fechaIngresoError != null,
                supportingText = state.fechaIngresoError?.let { { Text(it) } },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                    }
                }
            )

            OutlinedTextField(
                value = state.sueldo,
                onValueChange = { onEvent(EditEmpleadoUiEvent.SueldoChanged(it)) },
                label = { Text("Sueldo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_sueldo"),
                isError = state.sueldoError != null,
                supportingText = state.sueldoError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!state.isNew) {
                    Button(
                        onClick = { onEvent(EditEmpleadoUiEvent.Delete) },
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
                    onClick = { onEvent(EditEmpleadoUiEvent.Save) },
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
