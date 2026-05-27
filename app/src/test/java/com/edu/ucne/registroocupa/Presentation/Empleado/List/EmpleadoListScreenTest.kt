package com.edu.ucne.registroocupa.Presentation.Empleado.List

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.edu.ucne.registroocupa.Dominio.Models.Empleado.Empleado
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
class EmpleadoListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsLoadingIndicator_whenLoading() {
        val state = EmpleadoListUiState(isLoading = true)
        composeRule.setContent {
            EmpleadoListBody(
                state = state,
                onEvent = { },
                onAddClick = { },
                onEditClick = { }
            )
        }
        composeRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    @Test
    fun rendersEmpleados_andInvokesDelete() {
        val empleados = listOf(
            Empleado(empleadoId = 1, nombres = "A", sueldo = 1.0, fechaIngreso = LocalDate.now(), sexo = "Masculino"),
            Empleado(empleadoId = 2, nombres = "B", sueldo = 1.0, fechaIngreso = LocalDate.now(), sexo = "Masculino")
        )
        var deletedId: Int? = null

        composeRule.setContent {
            EmpleadoListBody(
                state = EmpleadoListUiState(Empleados = empleados),
                onEvent = {
                    if (it is EmpleadoListUiEvent.Delete) deletedId = it.id
                },
                onAddClick = { },
                onEditClick = { }
            )
        }

        composeRule.onNodeWithTag("Empleados_list").assertIsDisplayed()
        composeRule.onAllNodesWithTag("Empleado_card_1").assertCountEquals(1)
        composeRule.onAllNodesWithTag("Empleado_card_2").assertCountEquals(1)

        composeRule.onNodeWithTag("btn_delete_2").performClick()
        assert(deletedId == 2)
    }

    @Test
    fun fabClick_invokesOnAddClick() {
        var created = false
        composeRule.setContent {
            EmpleadoListBody(
                state = EmpleadoListUiState(),
                onEvent = { },
                onAddClick = { created = true },
                onEditClick = { }
            )
        }
        composeRule.onNodeWithTag("fab_add").performClick()
        assert(created)
    }
}
