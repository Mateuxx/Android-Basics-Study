package br.com.alura.ceep.webclient.model

import br.com.alura.ceep.model.Nota

/**
 * Resposta do ID no qual recebemos
 * Essa classe é para tratar esse caso e especifico
 *
 * Podemo ser Nulos caso não recebam algo da API
 */
class NotaResposta (
    //Criação das properties
    val id: String?,
    val titulo: String?,
    val descricao: String?,
    val imagem: String?
){
    /**
     * Cria sempre nota toda vez que a gente quer pegar esse get
     *
     */
    val nota : Nota get() = Nota (
        id = 0,
        titulo = titulo ?: "",
        descricao = descricao ?: "",
        imagem = imagem ?: ""
    )

}