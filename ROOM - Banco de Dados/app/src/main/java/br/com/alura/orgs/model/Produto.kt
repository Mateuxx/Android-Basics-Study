package br.com.alura.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


/*

   ROOM: https://developer.android.com/training/data-storage/room?hl=pt-br#sample-implementation

   A persistência de dados local pode ser muito útil para apps que processam quantidades não
   triviais de dados estruturados. O caso de uso mais comum é armazenar em cache partes importantes
   de dados para que, quando o dispositivo não puder acessar a rede, o usuário ainda consiga ter
   acesso a esse conteúdo off-line.

   Entidade -> Cada entidade corresponde a uma tabela no banco de dados associado do Room, e cada
   instância de uma entidade representa uma linha de dados na tabela correspondente.

   Ou seja, da pra usar vários esquemas de DE banco de dados em SQL!!




 */

// Essa data class abaixo corresponde a uma tabela chamada de Produto e tem as seguintes linhas e colunas:
@Entity
@Parcelize
data class Produto(
        @PrimaryKey(autoGenerate = true) // pode ser definida manualmente um PK, mas desse jeito ela geral de forma automatica!
        val id: Long = 0L, // Difere cada produto por esse id. Um produto por ter os mesmo nome ou descrição, porem o ID nunca será o mesmo
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val imagem: String? = null
) : Parcelable
