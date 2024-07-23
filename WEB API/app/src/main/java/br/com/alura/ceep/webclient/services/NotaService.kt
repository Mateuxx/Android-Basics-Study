package br.com.alura.ceep.webclient.services

import br.com.alura.ceep.webclient.model.NotaRequisicao
import br.com.alura.ceep.webclient.model.NotaResposta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotaService {
    /**
     * Busca no corpo da requisição  na respota esqperando uma lista do tipo nota
     * localhost:8080/notas
     */
    @GET("notas")
    suspend fun  buscaTodas() : List<NotaResposta>

    /**
     * Colocar e salvar as notas baseadas no id -> @Path
     * Represesnta o corpo da requisiçção -> @Body
     *
     * Para saber se de fato salvou corretamente na API devemos usar
     */
    @PUT("notas/{id}")
    suspend fun salva(
        @Path("id") id: String,
        @Body nota: NotaRequisicao
    ): Response<NotaResposta>

}