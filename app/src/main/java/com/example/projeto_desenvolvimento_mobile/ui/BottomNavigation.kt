package com.example.projeto_desenvolvimento_mobile.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class ItemAba(val rota: String, val rotulo: String, val icone: ImageVector)

val abas = listOf(
    ItemAba(Rotas.HOME, "Home", Icons.Default.Home),
    ItemAba(Rotas.UNIDADES, "Unidades", Icons.Default.Place),
    ItemAba(Rotas.CONSULTAS, "Consultas", Icons.Default.DateRange),
    ItemAba(Rotas.NOTICIAS, "Notícias", Icons.Default.Notifications)
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    NavigationBar {
        abas.forEach { aba ->
            NavigationBarItem(
                selected = rotaAtual == aba.rota,
                onClick = {
                    navController.navigate(aba.rota) {
                        // volta até a Home para as abas não empilharem umas sobre as outras
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(aba.icone, contentDescription = aba.rotulo) },
                label = { Text(aba.rotulo) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    indicatorColor = VerdePrincipal
                )
            )
        }
    }
}
