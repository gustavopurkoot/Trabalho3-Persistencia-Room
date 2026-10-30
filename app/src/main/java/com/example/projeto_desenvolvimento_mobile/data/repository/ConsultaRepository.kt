package com.example.projeto_desenvolvimento_mobile.data.repository

import com.example.projeto_desenvolvimento_mobile.data.Consulta
import com.example.projeto_desenvolvimento_mobile.data.ConsultaDAO
import kotlinx.coroutines.flow.Flow

// Único ponto que fala com o ConsultaDAO. O ViewModel só conhece o Repository.
class ConsultaRepository(private val dao: ConsultaDAO) {

    fun buscarTodos(): Flow<List<Consulta>> = dao.buscarTodos()

    suspend fun inserir(consulta: Consulta) = dao.inserir(consulta)

    suspend fun atualizar(consulta: Consulta) = dao.atualizar(consulta)

    suspend fun deletar(consulta: Consulta) = dao.deletar(consulta)

    suspend fun deletarPorUnidade(unidadeId: Int) = dao.deletarPorUnidade(unidadeId)
}
