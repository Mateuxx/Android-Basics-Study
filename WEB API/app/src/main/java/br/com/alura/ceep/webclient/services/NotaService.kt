package br.com.alura.ceep.webclient.services

import br.com.alura.ceep.model.Nota
import retrofit2.Call
import retrofit2.http.GET

interface NotaService {
    /**
     * Busca no corpo da requisição  na respota esqperando uma lista do tipo nota
     */
    @GET
    fun buscaTodas(): Call<List<Nota>>
}