package com.edu.ucne.registroocupa.Presentation.Empleado.Edit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class EmpleadoEditScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun userCanTypeAndSave() {
        var lastEvent: EditEmpleadoUiEvent? = null
        composeRule.setContent {
            EditEmpleadoBody(
                state = EditEmpleadoUiState(isNew = true),
                onEvent = { lastEvent = it },
                onBack = {}
            )
        }

        composeRule.onNodeWithTag("input_nombres").assertIsDisplayed().performTextInput("Empleado X")
        composeRule.onNodeWithTag("input_sueldo").assertIsDisplayed().performTextInput("1500")
        composeRule.onNodeWithTag("btn_guardar").performClick()

        assert(lastEvent is EditEmpleadoUiEvent.Save)
    }

    @Test
    fun showDeleteWhenEditing_andClickEmitsDelete() {
        var lastEvent: EditEmpleadoUiEvent? = null
        composeRule.setContent {
            EditEmpleadoBody(
                state = EditEmpleadoUiState(isNew = false),
                onEvent = { lastEvent = it },
                onBack = {}
            )
        }
        composeRule.onNodeWithTag("btn_eliminar").assertIsDisplayed().performClick()
        assert(lastEvent is EditEmpleadoUiEvent.Delete)
    }
}
