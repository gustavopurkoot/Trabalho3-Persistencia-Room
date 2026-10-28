package com.example.projeto_desenvolvimento_mobile

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.projeto_desenvolvimento_mobile.data.AppDatabase
import com.example.projeto_desenvolvimento_mobile.data.Consulta
import com.example.projeto_desenvolvimento_mobile.data.Unidade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Mesma API de antes (as telas quase não mudam), mas agora tudo passa pelo Room.
// As listas continuam sendo "estado" do Compose: quando o banco é lido, a tela atualiza sozinha.
class AppViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getDatabase(app)
    private val unidadeDao = db.unidadeDAO()
    private val consultaDao = db.consultaDAO()

    var unidades by mutableStateOf<List<Unidade>>(emptyList())
        private set
    var consultas by mutableStateOf<List<Consulta>>(emptyList())
        private set

    init {
        carregar()
    }

    // toda operação de banco roda no Dispatchers.IO, com try/catch
    private fun rodar(erro: String, acao: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                acao()
            } catch (e: Exception) {
                Log.e(erro, "Msg ${e.message}")
            }
        }
    }

    private suspend fun recarregar() {
        unidades = unidadeDao.buscarTodas()
        consultas = consultaDao.buscarTodas()
    }

    fun carregar() = rodar("Erro ao carregar") { recarregar() }

    // ---------- Unidades ----------
    fun adicionarUnidade(nome: String, endereco: String, distanciaKm: Int, telefone: String) =
        rodar("Erro ao inserir unidade") {
            unidadeDao.inserir(
                Unidade(
                    nome = nome,
                    endereco = endereco,
                    distanciaKm = distanciaKm,
                    telefone = telefone
                )
            )
            recarregar()
        }

    fun removerUnidade(id: Int) = rodar("Erro ao remover unidade") {
        unidades.find { it.id == id }?.let {
            consultaDao.deletarPorUnidade(id)
            unidadeDao.deletar(it)
        }
        recarregar()
    }

    // usada pelas outras telas (lê da lista já carregada)
    fun buscarUnidade(id: Int): Unidade? = unidades.find { it.id == id }

    // usada na tela de detalhes: busca direto no banco pelo id
    suspend fun buscarUnidadeNoBanco(id: Int): Unidade? = withContext(Dispatchers.IO) {
        try {
            unidadeDao.buscarPorId(id)
        } catch (e: Exception) {
            Log.e("Erro ao buscar unidade", "Msg ${e.message}")
            null
        }
    }

    // ---------- Consultas ----------
    fun adicionarConsulta(unidadeId: Int, especialidade: String, data: String, observacoes: String) =
        rodar("Erro ao inserir consulta") {
            consultaDao.inserir(
                Consulta(
                    unidadeId = unidadeId,
                    especialidade = especialidade,
                    data = data,
                    observacoes = observacoes
                )
            )
            recarregar()
        }

    fun removerConsulta(id: Int) = rodar("Erro ao remover consulta") {
        consultas.find { it.id == id }?.let { consultaDao.deletar(it) }
        recarregar()
    }

    fun buscarConsulta(id: Int): Consulta? = consultas.find { it.id == id }

    fun alternarRealizada(id: Int) = rodar("Erro ao atualizar consulta") {
        consultas.find { it.id == id }?.let {
            consultaDao.atualizar(it.copy(realizada = !it.realizada))
        }
        recarregar()
    }

    fun atualizarObservacoes(id: Int, observacoes: String) = rodar("Erro ao editar consulta") {
        consultas.find { it.id == id }?.let {
            consultaDao.atualizar(it.copy(observacoes = observacoes))
        }
        recarregar()
    }

    fun consultasDaUnidade(unidadeId: Int): List<Consulta> =
        consultas.filter { it.unidadeId == unidadeId }
}