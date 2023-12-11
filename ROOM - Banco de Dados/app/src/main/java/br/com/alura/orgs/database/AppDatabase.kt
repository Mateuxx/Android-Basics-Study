package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.coverter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto


/*
DATA base -> entities se refencia a uma classe que faz referencia de uma classe do jeito abaixo:
obs: Não é uma instancia de uma classe!!

Annotation TypeConverters -> A
 */
@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        /*
                Função para criar a instação para a criaçao de um Banco de Dados, Se cria na biblioteca do ROOM
                Usando o Companion Object Para se poder passar de forma estática.
                 */
        fun instancia(context: Context): AppDatabase {
                return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db" // NOme do Bd que foi Construido!!
            ).allowMainThreadQueries()
                .build()
        }
    }
}
