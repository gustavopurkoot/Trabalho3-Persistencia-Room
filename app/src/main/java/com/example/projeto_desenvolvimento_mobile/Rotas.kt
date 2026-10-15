package com.example.projeto_desenvolvimento_mobile

object Rotas {
    // abas do BottomNavigation
    const val HOME = "home"
    const val UNIDADES = "unidades"
    const val CONSULTAS = "consultas"
    const val NOTICIAS = "noticias"

    // telas com argumento (o {id} é preenchido nas funções abaixo)
    const val DETALHE_UNIDADE = "detalhe_unidade/{id}"
    const val DETALHE_CONSULTA = "detalhe_consulta/{id}"
    const val NOVA_CONSULTA = "nova_consulta?unidadeId={unidadeId}"

    fun detalheUnidade(id: Int) = "detalhe_unidade/$id"
    fun detalheConsulta(id: Int) = "detalhe_consulta/$id"
    fun novaConsulta(unidadeId: Int = -1) = "nova_consulta?unidadeId=$unidadeId"
}
