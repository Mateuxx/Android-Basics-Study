package br.com.alura.ceep.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Nota(
    @PrimaryKey
    //Gerando o UUID
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val descricao: String,
    val imagem: String? = null,
    @ColumnInfo(defaultValue = "0")
    val sincronizada: Boolean = false, //Flag para sincronização dos dados com API
    @ColumnInfo(defaultValue = "0")
    val desativada: Boolean = false //Flag de desativação - deletar uma nota
)
