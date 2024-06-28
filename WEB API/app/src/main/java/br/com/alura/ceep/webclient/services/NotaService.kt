package br.com.alura.ceep.webclient.services

import br.com.alura.ceep.model.Nota
import retrofit2.Call
import retrofit2.http.GET

interface NotaService {
    /**
     * Busca no corpo da requisição  na respota esqperando uma lista do tipo nota
     * localhost:8080/notas
     */
    @GET("notas")
    fun buscaTodas(): Call<List<Nota>>
}