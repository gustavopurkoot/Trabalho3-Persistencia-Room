package com.example.projeto_desenvolvimento_mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaNoticias() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Color(0xFF008C3A))
                .padding(horizontal = 27.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notícias e Saúde",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )


            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                CartaoNoticia(
                    categoria = "DICAS DE SAÚDE",
                    data = "Há 2 horas",
                    titulo = "Como melhorar sua qualidade de sono",
                    resumo = "Especialistas recomendam criar uma rotina noturna e evitar telas por pelo menos uma hora antes de dormir."
                )
            }

            item {
                CartaoNoticia(
                    categoria = "NOVIDADE",
                    data = "Ontem",
                    titulo = "Nova ala inaugurada no Hospital Central",
                    resumo = "O novo espaço conta com mais de 30 leitos modernos e uma equipe especializada pronta para atender."
                )
            }

            item {
                CartaoNoticia(
                    categoria = "PREVENÇÃO",
                    data = "12 de Outubro",
                    titulo = "Campanha de vacinação disponível",
                    resumo = "Todas as nossas unidades estarão disponibilizando o imunizante gratuitamente para clientes."
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun CartaoNoticia(categoria: String, data: String, titulo: String, resumo: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color(0xFFE0E0E0))
        )


        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = categoria, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF008C3A))
                Text(text = data, fontSize = 11.sp, color = Color(0xFF777777))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = titulo,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF160707),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = resumo,
                fontSize = 13.sp,
                color = Color(0xFF777777),
                lineHeight = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
fun PreviewTelaNoticias() {
    TelaNoticias()
}