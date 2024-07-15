package br.com.alura.ceep.webclient

import android.util.Log
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.services.NotaService
import java.lang.Exception

/**
 * Similar ao Dao do Room, porem focado para requisições web
 */
class NotaWebClient {

    private val notaService: NotaService =
        RetrofitInicializador().notaService

    suspend fun buscaTodas(): List<Nota>? {
        return try { //Evitar quebra do app por exception
            val notasResposta = notaService.buscaTodas()
            //Faz a conversão das requisições das notas do retrofit
            return notasResposta.map { notaResposta ->
                notaResposta.nota
            }
        }
        catch (e: Exception) {
            Log.e("NotaWebClient", "buscaTodas: ", e)
            null //retornar como nullable caso a gente não tenha aquilo que de fato queremos
        }
    }
}