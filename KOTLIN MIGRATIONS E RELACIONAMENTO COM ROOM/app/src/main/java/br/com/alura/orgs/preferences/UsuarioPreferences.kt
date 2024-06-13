package br.com.alura.orgs.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// Ele cria o nosso data Store, e ao que ele está relacionado, no caso sessão usuario
//representa tudo que salavremos com o data store
/**
 * Quando fazemos uma autenticação realizamos uma escrita!
 * Ele cria o nosso data Store, e ao que ele está relacionado, no caso sessão usuario
 * representa tudo que salavremos com o data store
 *
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessao_usuario")

/**
 * um jeito de fazer estatico para melhora o codigo
 */
val usuarioLogadoPreferences = stringPreferencesKey("usuarioLogado")
