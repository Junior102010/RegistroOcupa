package com.edu.ucne.registroocupa.Presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edu.ucne.registroocupa.Presentation.Ocupacion.Edit.EditOcupacionScreen
import com.edu.ucne.registroocupa.Presentation.Ocupacion.List.OcupacionListScreen
import com.edu.ucne.registroocupa.Presentation.navigation.Screen

@Composable
fun OcupacionNavHost(
    navController: NavHostController = rememberNavController()
)
{
    NavHost(
        navController =  navController,
        startDestination = Screen.OcupacionList
    )
    {
        composable<Screen.OcupacionList>{
            OcupacionListScreen(
                onAddOcupacion = {
                    navController.navigate(Screen.OcupacionEdit(0))
                },
                onEditOcupacion = {id ->
                    navController.navigate(Screen.OcupacionEdit(ocupacionId = id))
                }
            )
        }

        composable<Screen.OcupacionEdit>{
            EditOcupacionScreen(

                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
