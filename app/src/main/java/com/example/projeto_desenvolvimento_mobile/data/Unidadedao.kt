package com.example.projeto_desenvolvimento_mobile.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface UnidadeDAO {

    @Insert
    suspend fun inserir(unidade: Unidade)

    @Query("SELECT * FROM unidades")
    suspend fun buscarTodas(): List<Unidade>

    // consulta extra, filtrando por campo (usada na tela de detalhes)
    @Query("SELECT * FROM unidades WHERE id = :id")
    suspend fun buscarPorId(id: Int): Unidade?

    @Update
    suspend fun atualizar(unidade: Unidade)

    @Delete
    suspend fun deletar(unidade: Unidade)
}