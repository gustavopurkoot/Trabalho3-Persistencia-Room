package com.example.projeto_desenvolvimento_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaConsultas(navController: NavController, viewModel: AppViewModel) {
    LaunchedEffect(Unit) { viewModel.carregar() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF008C3A))
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Minhas Consultas",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // abre o formulário de nova consulta
            IconButton(onClick = { navController.navigate(Rotas.novaConsulta()) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agendar consulta",
                    tint = Color.White
                )
            }
        }

        if (viewModel.consultas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhuma consulta agendada. Toque no + para agendar.",
                    color = Color(0xFF666666),
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                items(viewModel.consultas, key = { it.id }) { consulta ->
                    CartaoConsulta(
                        consulta = consulta,
                        nomeUnidade = viewModel.buscarUnidade(consulta.unidadeId)?.nome
                            ?: "Unidade removida",
                        onAbrir = { navController.navigate(Rotas.detalheConsulta(consulta.id)) },
                        onAlternar = { viewModel.alternarRealizada(consulta.id) },
                        onRemover = { viewModel.removerConsulta(consulta.id) }
                    )
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
fun CartaoConsulta(
    consulta: Consulta,
    nomeUnidade: String,
    onAbrir: () -> Unit,
    onAlternar: () -> Unit,
    onRemover: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAbrir() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // marcar como realizada
            Checkbox(
                checked = consulta.realizada,
                onCheckedChange = { onAlternar() },
                colors = CheckboxDefaults.colors(checkedColor = VerdePrincipal)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = consulta.especialidade,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (consulta.realizada) Color(0xFF888888) else TextoEscuro,
                    textDecoration = if (consulta.realizada) TextDecoration.LineThrough else null
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${consulta.data}  •  $nomeUnidade",
                    fontSize = 13.sp,
                    color = Color(0xFF777777)
                )
            }

            IconButton(onClick = onRemover) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover consulta",
                    tint = Color(0xFFB00020)
                )
            }
        }
    }
}

