package com.example.projeto_desenvolvimento_mobile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun TelaPrincipal() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    // a barra de baixo só aparece nas 4 telas principais (nos detalhes e no formulário some)
    val mostrarBarra = rotaAtual == null || abas.any { it.rota == rotaAtual }

    Scaffold(
        containerColor = VerdePrincipal,
        bottomBar = { if (mostrarBarra) BottomNavBar(navController) }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
