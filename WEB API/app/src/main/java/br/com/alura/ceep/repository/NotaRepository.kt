package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.NotaWebClient
import kotlinx.coroutines.flow.Flow

class NotaRepository(
    private val dao: NotaDao,
    private val webClient: NotaWebClient
) {
    /**
     * busca todas la no DB
     */
    fun buscaTodas() : Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    /**
     * Busca as notas da api e salva no banco de dados
     */
    suspend fun atualizaTodas () {
        webClient.buscaTodas()?.let { notas ->
            dao.salva(notas)
        }
    }

    //Apenas o encapsulamento da parte do Repository
    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(id: String) {
        return dao.remove(id)
    }

    /**
     * Encapsulamento no qual salva na API
     */
    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        webClient.salva(nota)
    }
}