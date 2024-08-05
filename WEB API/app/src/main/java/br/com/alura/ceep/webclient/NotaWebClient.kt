package br.com.alura.ceep.webclient

import android.util.Log
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaRequisicao
import br.com.alura.ceep.webclient.model.NotaResposta
import br.com.alura.ceep.webclient.services.NotaService
import retrofit2.Response
import java.lang.Exception

class NotaWebClient {

    private val TAG: String? by lazy { "NotaWebClient" }
    private val notaService: NotaService =
        RetrofitInicializador().notaService

    suspend fun buscaTodas(): List<Nota>? {
        return try { //Evitar quebra do app por exception
            val notasResposta = notaService.buscaTodas()
            //Faz a conversão das requisições das notas do retrofit
            return notasResposta.map { notaResposta ->
                notaResposta.nota
            }
        } catch (e: Exception) {
            Log.e("NotaWebClient", "buscaTodas: ", e)
            null //retornar como nullable caso a gente não tenha aquilo que de fato queremos
        }
    }

    /**
     * Salva envia os dados da nota do seu aplicativo para a API
     */
    suspend fun salva(nota: Nota): Boolean {
        try {
            /**
             * val resposta -> recebe o resultado do tipo Response<NotaResposta>
             * Alem disso, faz as operações  de atribuição abaixo
             */
            val resposta: Response<NotaResposta> = notaService.salva(

                nota.id, NotaRequisicao(
                    titulo = nota.titulo,
                    descricao = nota.descricao,
                    imagem = nota.imagem
                )
            )
            // se a requisição deu certo  retorna true caso contrario, falso
            return resposta.isSuccessful
        } catch (e: Exception) {
            Log.e("NotaWebClient", "salva: falaha ao tentar salvar", e)
        }
        return false
    }

    /**
     * Remover uma nota la na web API
     */
    suspend fun remove(id: String): Boolean {
        try {
            notaService.remove(id)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "remove: Falha ao tentar removar a nota", e)
        }
        return false
    }
}