package edu.ucne.yudelkaguillen_p1_ap2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.yudelkaguillen_p1_ap2.navigation.Screen.ListScreen
import edu.ucne.yudelkaguillen_p1_ap2.navigation.Screen.RegistroScreen
import edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta.VentaScreen
import edu.ucne.yudelkaguillen_p1_ap2.presentation.venta.VentaListScreen
import edu.ucne.yudelkaguillen_p1_ap2.ui.theme.YudelkaGuillen_P1_AP2Theme

@Composable
fun NavHostExamen(
    navHost: NavHostController
) {
   NavHost(
        navController = navHost,
        startDestination = Screen.ListScreen
    ) {
      composable<Screen.ListScreen>{
          VentaListScreen(
              onEdit = {navHost.navigate(Screen.RegistroScreen(it))},
              onAdd = {navHost.navigate(Screen.RegistroScreen(0))}

          )
      }
        composable<Screen.RegistroScreen>{
            val args = it.toRoute<Screen.RegistroScreen>()
            VentaScreen(
                goVentaList = {
                    navHost.navigate(
                        Screen.ListScreen
                    )},
                goBack = {
                    navHost.navigateUp()
                },
                ventaId = args.id
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YudelkaGuillen_P1_AP2Theme {
        NavHostExamen(rememberNavController())
    }
}
