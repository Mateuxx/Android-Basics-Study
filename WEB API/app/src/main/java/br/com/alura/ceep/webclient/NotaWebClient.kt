package br.com.alura.ceep.webclient

import android.util.Log
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaRequisicao
import br.com.alura.ceep.webclient.model.NotaResposta
import br.com.alura.ceep.webclient.services.NotaService
import retrofit2.Response
import java.lang.Exception

/**
 * Similar ao Dao do Room, porem focado para requisições web
 */
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
    suspend fun salva(nota: Nota) {
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
            //checa se a operação foi bem sucessidida na API
            if (resposta.isSuccessful) {
                Log.i(
                    TAG,
                    "salva: Nota salva com sucesso!"
                ) //as vezes uma nota salva com sucesso nao nos dar o resultado que esperavamos la na a1pi
            }
            else {
                Log.i(TAG, "salva: nota não foi salva!")
            }

        } catch (e: Exception) {
            Log.e("NotaWebClient", "salva: falaha ao tentar salvar", e)
        }
    }
}