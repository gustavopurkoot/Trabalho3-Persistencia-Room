package com.example.projeto_desenvolvimento_mobile.data.repository

import com.example.projeto_desenvolvimento_mobile.data.Unidade
import com.example.projeto_desenvolvimento_mobile.data.UnidadeDAO
import kotlinx.coroutines.flow.Flow

// Único ponto que fala com o UnidadeDAO. O ViewModel só conhece o Repository.
class UnidadeRepository(private val dao: UnidadeDAO) {

    fun buscarTodos(): Flow<List<Unidade>> = dao.buscarTodos()

    suspend fun inserir(unidade: Unidade) = dao.inserir(unidade)

    suspend fun atualizar(unidade: Unidade) = dao.atualizar(unidade)

    suspend fun deletar(unidade: Unidade) = dao.deletar(unidade)
}
