package com.example.projeto_desenvolvimento_mobile

// Tipo de item 1: unidade de atendimento (hospital/clínica)
data class Unidade(
    val id: Int,
    val nome: String,
    val endereco: String,
    val distanciaKm: Int,
    val telefone: String
)

// Tipo de item 2: consulta agendada. Guarda o id da unidade onde acontece.
data class Consulta(
    val id: Int,
    val unidadeId: Int,
    val especialidade: String,
    val data: String,
    val observacoes: String,
    val realizada: Boolean = false
)
