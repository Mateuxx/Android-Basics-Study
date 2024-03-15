package br.com.alura.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.database.dao.UsuarioDao
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.model.Usuario

@Database(
    entities = [
        Produto::class,
        //Usuario::class // adicionando as entidades aqui
        // A CADA MUDANÇA TEMOS QUE REVERTER AONDE ESTAVAMOS PARA GIRAR UMA
        // NOVA NOVA MIGRATION POR ISSO AS COISAS QUE MUDAMOS AO EXEMPLIFICAR ESTÃO COMENTADAS.
    ],
    // A cada mudança no room seja qualquer coisa mesmo a gente tem que mudar a versão aqui
    // pq seria um shema novo! caso o contrário dará erro!!!!!!
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao

    //abstract  fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            // A cada mudança temos que chamar essa função!!
            // Porem a cada mudanã será perdido todas as informações! - Não é uma boa Prática
            // Não é recomendado!
            // Interessante apenas quando estamso desenvolvendo nosso App!
            ).build().also {
                db = it
            }
        }
    }
}