package com.example.projeto_desenvolvimento_mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projeto_desenvolvimento_mobile.AppViewModel

@Composable
fun TelaNovaConsulta(navController: NavController, viewModel: AppViewModel, unidadeIdInicial: Int) {
    var especialidade by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }

    // se veio da tela de uma unidade, ela já começa selecionada (-1 = nenhuma)
    var unidadeSelecionada by remember {
        mutableStateOf(if (viewModel.buscarUnidade(unidadeIdInicial) != null) unidadeIdInicial else -1)
    }

    val valido = especialidade.isNotBlank() && data.isNotBlank() && unidadeSelecionada != -1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        BarraTopo("Nova Consulta", navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = especialidade,
                onValueChange = { especialidade = it },
                label = { Text("Especialidade") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = data,
                onValueChange = { data = it },
                label = { Text("Data (dd/mm/aaaa)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = observacoes,
                onValueChange = { observacoes = it },
                label = { Text("Observações") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Unidade",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextoEscuro
            )

            if (viewModel.unidades.isEmpty()) {
                Text(
                    text = "Cadastre uma unidade antes de agendar.",
                    fontSize = 14.sp,
                    color = TextoCinza
                )
                BotaoVerde("IR PARA UNIDADES") {
                    navController.navigate(Rotas.UNIDADES)
                }
            } else {
                viewModel.unidades.forEach { unidade ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { unidadeSelecionada = unidade.id },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = unidadeSelecionada == unidade.id,
                            onClick = { unidadeSelecionada = unidade.id },
                            colors = RadioButtonDefaults.colors(selectedColor = VerdePrincipal)
                        )
                        Text(
                            text = unidade.nome,
                            fontSize = 15.sp,
                            color = TextoEscuro
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            BotaoVerde(texto = "AGENDAR", enabled = valido) {
                viewModel.adicionarConsulta(
                    unidadeId = unidadeSelecionada,
                    especialidade = especialidade.trim(),
                    data = data.trim(),
                    observacoes = observacoes.trim()
                )
                navController.popBackStack()
            }
        }
    }
}
