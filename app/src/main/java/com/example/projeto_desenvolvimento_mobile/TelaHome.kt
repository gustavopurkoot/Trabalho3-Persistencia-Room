package com.example.projeto_desenvolvimento_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun TelaHome(navController: NavController, viewModel: AppViewModel) {
    // token gerado de verdade ao clicar em GERAR TOKEN
    var token by remember { mutableStateOf<String?>(null) }
    val proximaConsulta = viewModel.consultas.firstOrNull { !it.realizada }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF008C3A))
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "OLÁ, Usuário!",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BotaoCirculoTopo(Icons.Default.Notifications, "Notícias") {
                    navController.navigate(Rotas.NOTICIAS)
                }
                BotaoCirculoTopo(Icons.Default.Place, "Unidades") {
                    navController.navigate(Rotas.UNIDADES)
                }
                BotaoCirculoTopo(Icons.Default.DateRange, "Consultas") {
                    navController.navigate(Rotas.CONSULTAS)
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                Text(
                    text = "Cartão Virtual",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFDCDCDC))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFC4C4C4))
                        )
                        Text(text = "Cidade - ES", fontSize = 12.sp, color = Color(0xFF555555))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "00 0000 00 000 000 - 000",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1C1C)
                        )

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFC4C4C4)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .size(12.dp)
                                .background(Color(0xFF888888)))
                        }
                    }

                    Text(
                        text = "Nome Completo do Paciente",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1C1C)
                    )

                    if (token != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Token: $token",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF008C3A)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        // AGENDAR: abre o formulário de nova consulta
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF008C3A))
                                .clickable { navController.navigate(Rotas.novaConsulta()) },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "AGENDAR",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // GERAR TOKEN: sorteia um código de 6 dígitos
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF008C3A))
                                .clickable { token = (100000..999999).random().toString() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "GERAR TOKEN",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Favoritos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ItemFavorito("Unidades", Icons.Default.Place, Modifier.weight(1f)) {
                            navController.navigate(Rotas.UNIDADES)
                        }
                        ItemFavorito("Consultas", Icons.Default.DateRange, Modifier.weight(1f)) {
                            navController.navigate(Rotas.CONSULTAS)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ItemFavorito("Agendar", Icons.Default.Add, Modifier.weight(1f)) {
                            navController.navigate(Rotas.novaConsulta())
                        }
                        ItemFavorito("Notícias", Icons.Default.Notifications, Modifier.weight(1f)) {
                            navController.navigate(Rotas.NOTICIAS)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Acesso Rápido",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFDCDCDC))
                        .padding(vertical = 4.dp)
                ) {
                    // mostra a próxima consulta pendente (dado real da lista)
                    if (proximaConsulta != null) {
                        ItemAcessoRapido(
                            texto = "Próxima consulta: ${proximaConsulta.especialidade} - ${proximaConsulta.data}",
                            icone = Icons.Default.DateRange
                        ) {
                            navController.navigate(Rotas.detalheConsulta(proximaConsulta.id))
                        }
                    } else {
                        ItemAcessoRapido(
                            texto = "Nenhuma consulta pendente - agendar agora",
                            icone = Icons.Default.Add
                        ) {
                            navController.navigate(Rotas.novaConsulta())
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFC4C4C4)))
                    ItemAcessoRapido("Ver unidades de atendimento", Icons.Default.Place) {
                        navController.navigate(Rotas.UNIDADES)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun BotaoCirculoTopo(icone: ImageVector, descricao: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icone,
            contentDescription = descricao,
            tint = Color(0xFF008C3A),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ItemFavorito(
    titulo: String,
    icone: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFDCDCDC))
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFC4C4C4)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icone,
                contentDescription = titulo,
                tint = Color(0xFF555555),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = titulo,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C)
        )
    }
}

@Composable
fun ItemAcessoRapido(texto: String, icone: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = Color(0xFF555555),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C)
        )
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
fun PreviewTelaHome() {
    TelaHome(rememberNavController(), AppViewModel())
}
