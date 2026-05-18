package com.edu.ucne.registroocupa.Presentation.Ocupacion.Edit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OcupacionEditScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun userCanTypeAndSave() {
        var lastEvent: EditOcupacionUiEvent? = null
        composeRule.setContent {
            EditOcupacionBody(
                state = EditOcupacionUiState(isNew = true),
                onEvent = { lastEvent = it },
                onBack = {}
            )
        }

        composeRule.onNodeWithTag("input_descripcion").assertIsDisplayed().performTextInput("Ocupacion X")
        composeRule.onNodeWithTag("input_sueldo").assertIsDisplayed().performTextInput("1500")
        composeRule.onNodeWithTag("btn_guardar").performClick()

        assert(lastEvent is EditOcupacionUiEvent.Save)
    }

    @Test
    fun showDeleteWhenEditing_andClickEmitsDelete() {
        var lastEvent: EditOcupacionUiEvent? = null
        composeRule.setContent {
            EditOcupacionBody(
                state = EditOcupacionUiState(isNew = false),
                onEvent = { lastEvent = it },
                onBack = {}
            )
        }
        composeRule.onNodeWithTag("btn_eliminar").assertIsDisplayed().performClick()
        assert(lastEvent is EditOcupacionUiEvent.Delete)
    }
}
