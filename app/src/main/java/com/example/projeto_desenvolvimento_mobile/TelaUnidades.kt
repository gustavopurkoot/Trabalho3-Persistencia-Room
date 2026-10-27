package com.example.projeto_desenvolvimento_mobile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TelaUnidadesAtendimento(navController: NavController, viewModel: AppViewModel) {
    LaunchedEffect(Unit) { viewModel.carregar() }
    var mostrarFormulario by remember { mutableStateOf(false) }
    var ordenarPorDistancia by remember { mutableStateOf(false) }

    val unidadesExibidas =
        if (ordenarPorDistancia) viewModel.unidades.sortedBy { it.distanciaKm }
        else viewModel.unidades.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
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
                text = "Unidade de Atendimento",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // mostra/esconde o formulário de nova unidade
            IconButton(onClick = { mostrarFormulario = !mostrarFormulario }) {
                Icon(
                    imageVector = if (mostrarFormulario) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = "Adicionar unidade",
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (mostrarFormulario) {
                item {
                    FormularioUnidade { nome, endereco, distancia, telefone ->
                        viewModel.adicionarUnidade(nome, endereco, distancia, telefone)
                        mostrarFormulario = false
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFD4D4D4)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Mapa - Unidades Próximas",
                        color = Color(0xFF666666),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE0E0E0))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AbaToggle("MINHAS UNIDADES", !ordenarPorDistancia, Modifier.weight(1f)) {
                        ordenarPorDistancia = false
                    }
                    AbaToggle("MAIS PRÓXIMAS", ordenarPorDistancia, Modifier.weight(1f)) {
                        ordenarPorDistancia = true
                    }
                }
            }

            if (unidadesExibidas.isEmpty()) {
                item {
                    Text(
                        text = "Nenhuma unidade cadastrada. Toque no + para adicionar.",
                        color = Color(0xFF666666),
                        fontSize = 14.sp
                    )
                }
            }

            items(unidadesExibidas, key = { it.id }) { unidade ->
                CartaoHospital(
                    unidade = unidade,
                    onAbrir = { navController.navigate(Rotas.detalheUnidade(unidade.id)) },
                    onRemover = { viewModel.removerUnidade(unidade.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun AbaToggle(texto: String, selecionada: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(6.dp))
            .background(if (selecionada) Color(0xFF008C3A) else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = if (selecionada) Color.White else Color(0xFF555555),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FormularioUnidade(onSalvar: (String, String, Int, String) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    val distanciaInt = distancia.toIntOrNull()
    val valido = nome.isNotBlank() && endereco.isNotBlank() &&
            telefone.isNotBlank() && distanciaInt != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE2E2E2))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Nova unidade",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextoEscuro
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text("Endereço") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = distancia,
            onValueChange = { novo -> distancia = novo.filter { it.isDigit() } },
            label = { Text("Distância (km)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = { Text("Telefone") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        BotaoVerde(
            texto = "ADICIONAR UNIDADE",
            enabled = valido
        ) {
            onSalvar(nome.trim(), endereco.trim(), distanciaInt ?: 0, telefone.trim())
        }
    }
}

@Composable
fun CartaoHospital(unidade: Unidade, onAbrir: () -> Unit, onRemover: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAbrir() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE2E2E2))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = unidade.nome,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C),
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onRemover) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remover ${unidade.nome}",
                        tint = Color(0xFFB00020)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = unidade.endereco,
                fontSize = 14.sp,
                color = Color(0xFF444444)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF008C3A))
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Distância: ${unidade.distanciaKm} KM",
                    fontSize = 13.sp,
                    color = Color(0xFF444444),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = unidade.telefone,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1C1C)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BotaoCartao(
                    texto = "VER DETALHES",
                    icone = Icons.Default.Info,
                    cor = Color(0xFF008C3A),
                    modifier = Modifier.weight(1f),
                    onClick = onAbrir
                )

                BotaoCartao(
                    texto = "LIGAR NO HOSPITAL",
                    icone = Icons.Default.Phone,
                    cor = Color(0xFF555555),
                    modifier = Modifier.weight(1.2f)
                ) {
                    // abre o discador do celular com o número (não precisa de permissão)
                    try {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(unidade.telefone)))
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Nenhum app de telefone disponível", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
private fun BotaoCartao(
    texto: String,
    icone: ImageVector,
    cor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(cor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icone,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = texto,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

