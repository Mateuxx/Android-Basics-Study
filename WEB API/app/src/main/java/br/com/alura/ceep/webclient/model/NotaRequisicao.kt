package br.com.alura.ceep.webclient.model

/**
 * Temos que criar um model apenas com os Parametros que temos para fazer a requisição, diferente
 * do DB, la não temos o valor relacionado ao id do usuário.
 * Representa o corpo da requisição da API.
 */
data class NotaRequisicao(
    val titulo: String,
    val descricao: String,
    val imagem: String? = null
)
