package edu.ucne.yudelkaguillen_p1_ap2.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
          Button(onClick = { navHost.navigate(Screen.RegistroScreen(0)) }) {
              Text(text = "Registro")
          }
      }
        composable<Screen.RegistroScreen>{
            Button(onClick = { navHost.navigate(Screen.ListScreen) }) {
                Text(text = ":D funciona")
            }
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