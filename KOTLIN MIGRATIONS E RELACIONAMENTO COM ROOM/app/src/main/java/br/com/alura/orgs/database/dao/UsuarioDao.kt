package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query(
        """
        SELECT * FROM Usuario 
        WHERE id = :usuarioId 
        AND senha = :senha"""
    ) // Bater com as informações com as coisas que estão na tabela de usuários
    suspend fun autentica(
        usuarioId: String,
        senha: String
    ): Usuario?

    /**
     * Buscar usuarios para buscar por id
     * @return flow -> para poder fazer atualizações automaticas
     */
    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    fun buscaPorId(usuarioId: String): Flow<Usuario>

}