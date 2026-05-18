package com.edu.ucne.registroocupa.Presentation.Ocupacion.List

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.edu.ucne.registroocupa.Dominio.Models.Ocupacion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OcupacionListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsLoadingIndicator_whenLoading() {
        val state = OcupacionListUiState(isLoading = true)
        composeRule.setContent {
            OcupacionListBody(
                state = state,
                onEvent = { },
                onAddClick = { },
                onEditClick = { }
            )
        }
        composeRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun rendersOcupaciones_andInvokesDelete() {
        val ocupaciones = listOf(
            Ocupacion(ocupacionesId = 1, descripcion = "A", sueldo = 1.0),
            Ocupacion(ocupacionesId = 2, descripcion = "b", sueldo = 1.0)
        )
        var deletedId: Int? = null

        composeRule.setContent {
            OcupacionListBody(
                state = OcupacionListUiState(ocupaciones = ocupaciones),
                onEvent = {
                    if (it is OcupacionListUiEvent.Delete) deletedId = it.id
                },
                onAddClick = { },
                onEditClick = { }
            )
        }

        composeRule.onNodeWithTag("Ocupaciones_list").assertIsDisplayed()
        composeRule.onAllNodesWithTag("ocupacion_card_1").assertCountEquals(1)
        composeRule.onAllNodesWithTag("ocupacion_card_2").assertCountEquals(1)

        composeRule.onNodeWithTag("btn_delete_2").performClick()
        assert(deletedId == 2)
    }

    @Test
    fun fabClick_invokesOnAddClick() {
        var created = false
        composeRule.setContent {
            OcupacionListBody(
                state = OcupacionListUiState(),
                onEvent = { },
                onAddClick = { created = true },
                onEditClick = { }
            )
        }
        composeRule.onNodeWithTag("fab_add").performClick()
        assert(created)
    }
}
