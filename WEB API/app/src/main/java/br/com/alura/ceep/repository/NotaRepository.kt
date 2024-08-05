package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.NotaWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotaRepository(
    private val dao: NotaDao,
    private val webClient: NotaWebClient
) {
    /**
     * busca todas la no DB
     */
    fun buscaTodas(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    /**
     * Busca as notas da api e salva no banco de dados
     */
    private suspend fun atualizaTodas() {
        webClient.buscaTodas()?.let { notas ->
            val notasSincronizadas = notas.map { nota ->
                nota.copy(sincronizada = true)
            }
            dao.salva(notasSincronizadas) //atualiza as infos que veio da web API
        }
    }

    //Apenas o encapsulamento da parte do Repository
    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(id: String) {
        dao.desativa(id) //faz a desativação internamente - SoftDelete (offline)
        if (webClient.remove(id)){ //remove la na api
            dao.remove(id)//para então podermos deletar de fato no db
        }
    }

    /**
     * Encapsulamento no qual salva na API
     */
    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        if (webClient.salva(nota)) {
            //transformar a nota numa copia sincronizada
            val notaSincronizada = nota.copy(sincronizada = true)
            dao.salva(notaSincronizada)
        }
    }

    /**
     * Notas que foram criadas offline (0) e com a que está na API para sincronizar
     */
    suspend fun sincroniza() {

        //Pegar as que foram deletadas e sincronizar na API
        val notasDesativadas = dao.buscaDesativadas().first()
        notasDesativadas.forEach {
            remove(it.id)
        }

        //pegar as notas não sincronizadas e sincronizar
        val notasNaoSincronizadas = dao.buscaNaoSincronizadas().first()
        notasNaoSincronizadas.forEach { notasNaoSincroziada ->
            salva(notasNaoSincroziada) //salva as notas sincronizadas na API
        }

        atualizaTodas()
    }
}