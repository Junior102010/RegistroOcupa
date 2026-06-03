package com.edu.ucne.registroocupa.Presentation.navigation

import HoraExtraAdaptiveScreen
import OcupacionAdaptiveScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edu.ucne.registroocupa.Presentation.Empleado.Edit.EditEmpleadoScreen
import com.edu.ucne.registroocupa.Presentation.Empleado.EmpleadoAdaptiveScreen
import com.edu.ucne.registroocupa.Presentation.Empleado.List.EmpleadoListScreen
import com.edu.ucne.registroocupa.Presentation.HoraExtra.Edit.EditHoraExtraScreen
import com.edu.ucne.registroocupa.Presentation.HoraExtra.List.HoraExtraListScreen
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
        composable<Screen.OcupacionList>
        {
            OcupacionAdaptiveScreen()
        }

        composable<Screen.EmpleadoList>
        {
            EmpleadoAdaptiveScreen()
        }

        composable<Screen.HoraExtraList>
        {
            HoraExtraAdaptiveScreen()
        }
    }
}
