package com.example.projeto_desenvolvimento_mobile.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UnidadeDAO {

    @Insert
    suspend fun inserir(unidade: Unidade)

    // Flow: o Room atualiza a lista sozinho sempre que a tabela muda
    @Query("SELECT * FROM unidades")
    fun buscarTodos(): Flow<List<Unidade>>

    // consulta extra, filtrando por campo
    @Query("SELECT * FROM unidades WHERE id = :id")
    suspend fun buscarPorId(id: Int): Unidade?

    @Update
    suspend fun atualizar(unidade: Unidade)

    @Delete
    suspend fun deletar(unidade: Unidade)
}
