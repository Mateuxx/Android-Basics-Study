package br.com.alura.orgs.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Colocar todas as intruções em SQL basicamente todas as mudanças vão ser colocadas aqui
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `Usuario` (`id` TEXT NOT NULL, `nome` TEXT NOT NULL, `senha` TEXT NOT NULL, PRIMARY KEY(`id`))"
        )
    }
}