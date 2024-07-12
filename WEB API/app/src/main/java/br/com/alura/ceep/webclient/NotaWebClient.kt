package br.com.alura.ceep.webclient

import android.util.Log
import br.com.alura.ceep.model.Nota

/**
 * Similar ao Dao do Room, porem focado para requisições web
 */
class NotaWebClient {
    suspend fun buscaTodas(): List<Nota> {
        val notasResposta = RetrofitInicializador().notaService.buscaTodas()
        //Faz a conversão das requisições das notas do retrofit
        return notasResposta.map { notaResposta ->
            notaResposta.nota
        }
    }
}