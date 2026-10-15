package com.example.projeto_desenvolvimento_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaDetalheConsulta(navController: NavController, viewModel: AppViewModel, id: Int) {
    val consulta = viewModel.buscarConsulta(id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        BarraTopo("Detalhes da Consulta", navController)

        if (consulta == null) {
            Text(
                text = "Consulta não encontrada.",
                color = TextoEscuro,
                modifier = Modifier.padding(20.dp)
            )
        } else {
            // combina dados das duas listas: a consulta e a unidade dela
            val unidade = viewModel.buscarUnidade(consulta.unidadeId)

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
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = consulta.especialidade,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LinhaInfo("Data", consulta.data)
                        LinhaInfo(
                            "Observações",
                            consulta.observacoes.ifBlank { "Sem observações" }
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.alternarRealizada(consulta.id) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = consulta.realizada,
                                onCheckedChange = { viewModel.alternarRealizada(consulta.id) },
                                colors = CheckboxDefaults.colors(checkedColor = VerdePrincipal)
                            )
                            Text(
                                text = "Consulta realizada",
                                fontSize = 15.sp,
                                color = TextoEscuro
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE2E2E2))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Unidade",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoEscuro
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        if (unidade == null) {
                            Text(
                                text = "Unidade removida.",
                                fontSize = 14.sp,
                                color = TextoCinza
                            )
                        } else {
                            LinhaInfo("Nome", unidade.nome)
                            LinhaInfo("Endereço", unidade.endereco)
                            LinhaInfo("Telefone", unidade.telefone)

                            // navegação secundária: vai para o detalhe da unidade
                            BotaoVerde("VER UNIDADE") {
                                navController.navigate(Rotas.detalheUnidade(unidade.id))
                            }
                        }
                    }
                }

                BotaoVerde("REMOVER CONSULTA") {
                    viewModel.removerConsulta(consulta.id)
                    navController.popBackStack()
                }
            }
        }
    }
}
