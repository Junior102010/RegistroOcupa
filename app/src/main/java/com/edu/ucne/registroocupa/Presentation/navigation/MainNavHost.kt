package com.edu.ucne.registroocupa.Presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edu.ucne.registroocupa.Presentation.Empleado.Edit.EditEmpleadoScreen
import com.edu.ucne.registroocupa.Presentation.Empleado.List.EmpleadoListScreen
import com.edu.ucne.registroocupa.Presentation.Ocupacion.Edit.EditOcupacionScreen
import com.edu.ucne.registroocupa.Presentation.Ocupacion.List.OcupacionListScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    innerPaidding : PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.EmpleadoList,
        modifier = Modifier.padding(innerPaidding)
    ) {
        composable<Screen.OcupacionList> {
            OcupacionListScreen(
                onAddOcupacion = {
                    navController.navigate(Screen.OcupacionEdit(0))
                },
                onEditOcupacion = { id ->
                    navController.navigate(Screen.OcupacionEdit(ocupacionId = id))
                }
            )
        }

        composable<Screen.OcupacionEdit> {
            EditOcupacionScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.EmpleadoList> {
            EmpleadoListScreen(
                onAddEmpleado = {
                    navController.navigate(Screen.EmpleadoEdit(0))
                },
                onEditEmpleado = { id ->
                    navController.navigate(Screen.EmpleadoEdit(empleadoId = id))
                }
            )
        }

        composable<Screen.EmpleadoEdit> {
            EditEmpleadoScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
