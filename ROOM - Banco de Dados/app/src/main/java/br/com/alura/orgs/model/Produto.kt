package br.com.alura.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal


/*
   A persistência de dados local pode ser muito útil para apps que processam quantidades não
   triviais de dados estruturados. O caso de uso mais comum é armazenar em cache partes importantes
   de dados para que, quando o dispositivo não puder acessar a rede, o usuário ainda consiga ter
   acesso a esse conteúdo off-line.

   Entidade -> 

   ROOM: https://developer.android.com/training/data-storage/room?hl=pt-br#sample-implementation


 */
@Entity
@Parcelize
data class Produto(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val imagem: String? = null
) : Parcelable
