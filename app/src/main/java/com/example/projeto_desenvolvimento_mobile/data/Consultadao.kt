package com.example.projeto_desenvolvimento_mobile.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsultaDAO {

    @Insert
    suspend fun inserir(consulta: Consulta)

    // Flow: o Room atualiza a lista sozinho sempre que a tabela muda
    @Query("SELECT * FROM consultas")
    fun buscarTodos(): Flow<List<Consulta>>

    @Update
    suspend fun atualizar(consulta: Consulta)

    @Delete
    suspend fun deletar(consulta: Consulta)

    // ao remover uma unidade, as consultas dela também saem do banco
    @Query("DELETE FROM consultas WHERE unidadeId = :unidadeId")
    suspend fun deletarPorUnidade(unidadeId: Int)
}
