package com.example.projeto_desenvolvimento_mobile

import androidx.room3.Entity
import androidx.room3.PrimaryKey

// Tipo de item 1: unidade de atendimento (hospital/clínica)
@Entity(tableName = "unidades")
data class Unidade(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val endereco: String,
    val distanciaKm: Int,
    val telefone: String
)

// Tipo de item 2: consulta agendada. Guarda o id da unidade onde acontece.
@Entity(tableName = "consultas")
data class Consulta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val unidadeId: Int,
    val especialidade: String,
    val data: String,
    val observacoes: String,
    val realizada: Boolean = false
)