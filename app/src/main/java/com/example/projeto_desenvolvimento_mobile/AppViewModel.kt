package com.example.projeto_desenvolvimento_mobile

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// As duas listas moram aqui para todas as telas enxergarem os mesmos dados.
// Ficam só na memória (mutableStateListOf), como o trabalho permite.
class AppViewModel : ViewModel() {

    val unidades = mutableStateListOf(
        Unidade(1, "Hospital Central", "Rua da Silva, 60", 8, "+55 41 99999-9999"),
        Unidade(2, "Hospital Santa Cruz", "Av. Brasil, 1200", 3, "+55 41 98888-8888"),
        Unidade(3, "Clínica Vida", "Rua das Flores, 45", 12, "+55 41 97777-7777")
    )

    val consultas = mutableStateListOf(
        Consulta(1, 1, "Cardiologia", "20/10/2026", "Levar exames anteriores")
    )

    private var proximoIdUnidade = 4
    private var proximoIdConsulta = 2

    // ---------- Unidades ----------
    fun adicionarUnidade(nome: String, endereco: String, distanciaKm: Int, telefone: String) {
        unidades.add(Unidade(proximoIdUnidade++, nome, endereco, distanciaKm, telefone))
    }

    fun removerUnidade(id: Int) {
        unidades.removeAll { it.id == id }
        // consultas de uma unidade removida deixam de fazer sentido
        consultas.removeAll { it.unidadeId == id }
    }

    fun buscarUnidade(id: Int): Unidade? = unidades.find { it.id == id }

    // ---------- Consultas ----------
    fun adicionarConsulta(unidadeId: Int, especialidade: String, data: String, observacoes: String) {
        consultas.add(Consulta(proximoIdConsulta++, unidadeId, especialidade, data, observacoes))
    }

    fun removerConsulta(id: Int) {
        consultas.removeAll { it.id == id }
    }

    fun buscarConsulta(id: Int): Consulta? = consultas.find { it.id == id }

    fun alternarRealizada(id: Int) {
        val indice = consultas.indexOfFirst { it.id == id }
        if (indice >= 0) {
            consultas[indice] = consultas[indice].copy(realizada = !consultas[indice].realizada)
        }
    }

    fun consultasDaUnidade(unidadeId: Int): List<Consulta> =
        consultas.filter { it.unidadeId == unidadeId }
}
