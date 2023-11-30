package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Produto


/*
* Interface que eh responsavel por chamar as querrys -> usar o SQLITE pelo ROOM. Como se fosse
* um "Framework" para uso do sqlite de forma mais simplificada.
*
* ROOM DOCUMENTAÇÃO: https://developer.android.com/training/data-storage/room#sample-implementation
* */

@Dao
interface ProdutoDao {
    /*
    Uso:
    @Notação()
    fun nomeDaFuncao : Função que ira performar esse comando que seria SQLITE
     */
    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert
    fun insereNoBanco(vararg produto: Produto)
}