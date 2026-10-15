package com.example.projeto_desenvolvimento_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaDetalheUnidade(navController: NavController, viewModel: AppViewModel, id: Int) {
    // busca a unidade certa pelo id recebido na rota
    val unidade = viewModel.buscarUnidade(id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        BarraTopo("Detalhes da Unidade", navController)

        if (unidade == null) {
            Text(
                text = "Unidade não encontrada.",
                color = TextoEscuro,
                modifier = Modifier.padding(20.dp)
            )
        } else {
            // informação calculada a partir da outra lista (consultas)
            val consultasDaUnidade = viewModel.consultasDaUnidade(unidade.id)
            val pendentes = consultasDaUnidade.count { !it.realizada }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE2E2E2))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = unidade.nome,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LinhaInfo("Endereço", unidade.endereco)
                        LinhaInfo("Distância", "${unidade.distanciaKm} KM")
                        LinhaInfo("Telefone", unidade.telefone)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE2E2E2))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Consultas nesta unidade",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total: ${consultasDaUnidade.size}  |  Pendentes: $pendentes",
                            fontSize = 14.sp,
                            color = Color(0xFF008C3A),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (consultasDaUnidade.isEmpty()) {
                            Text(
                                text = "Nenhuma consulta marcada aqui ainda.",
                                fontSize = 14.sp,
                                color = TextoCinza
                            )
                        }

                        // navegação secundária: cada consulta abre o detalhe dela
                        consultasDaUnidade.forEach { consulta ->
                            Text(
                                text = "${consulta.especialidade} - ${consulta.data}",
                                fontSize = 14.sp,
                                color = TextoEscuro,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(Rotas.detalheConsulta(consulta.id))
                                    }
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                }

                BotaoVerde("AGENDAR NESTA UNIDADE") {
                    navController.navigate(Rotas.novaConsulta(unidade.id))
                }
            }
        }
    }
}
