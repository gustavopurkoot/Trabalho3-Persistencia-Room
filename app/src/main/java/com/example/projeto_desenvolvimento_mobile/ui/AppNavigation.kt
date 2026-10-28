package com.example.projeto_desenvolvimento_mobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projeto_desenvolvimento_mobile.AppViewModel

// NavHost central: todas as 7 telas do app passam por aqui.
@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Rotas.HOME,
        modifier = modifier
    ) {
        composable(Rotas.HOME) { TelaHome(navController, viewModel) }

        composable(Rotas.NOTICIAS) { TelaNoticias() }

        composable(Rotas.UNIDADES) { TelaUnidadesAtendimento(navController, viewModel) }

        composable(
            Rotas.DETALHE_UNIDADE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: -1
            TelaDetalheUnidade(navController, viewModel, id)
        }

        composable(Rotas.CONSULTAS) { TelaConsultas(navController, viewModel) }

        composable(
            Rotas.DETALHE_CONSULTA,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: -1
            TelaDetalheConsulta(navController, viewModel, id)
        }

        composable(
            Rotas.NOVA_CONSULTA,
            arguments = listOf(navArgument("unidadeId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { entrada ->
            val unidadeId = entrada.arguments?.getInt("unidadeId") ?: -1
            TelaNovaConsulta(navController, viewModel, unidadeId)
        }
    }
}
