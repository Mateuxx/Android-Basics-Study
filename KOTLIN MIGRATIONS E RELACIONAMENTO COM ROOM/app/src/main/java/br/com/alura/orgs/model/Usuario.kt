package br.com.alura.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    /**
     * Definindo o id como primary key
     */
    @PrimaryKey
    val id: String,
    val nome: String,
    val senha: String
)
