package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.model.Usuario
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Activity base -> Utilizada apenas para compartilharmos as logicas que estão aqui com outras
 * activities
 *
 * Ele só vai pegar essa informação quando de fato tiver essa info.
 */
abstract class UsuarioBaseActivity : AppCompatActivity() {


    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    /***
     * Usar como flow -> Estrutura para reagir a thread!
     * Caso contrario irar esperar a thread toda ser finalizad
     * Sempre passadno um estado inicial
     */
    private var _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected var usuario: StateFlow<Usuario?> = _usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }


    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            }
                ?: vaiParaLogin() // Elvis operator para setar caso seja nulo o usuario e mandar
            //para tela de login
            // ele vai jogar para a tela de login e não inicializar com ela
        }
    }

    private suspend fun buscaUsuario(usuarioId: String) {
        /**
         * first() -> retorna a primeiro elemento de emitido por um flow
         * e cancela o flows collection. Como se cortasse algumas coisas
         * ele pode devolver nullPointerExecption
         */
        /**
         * first() -> retorna a primeiro elemento de emitido por um flow
         * e cancela o flows collection. Como se cortasse algumas coisas
         * ele pode devolver nullPointerExecption
         */
        _usuario.value = usuarioDao.buscaPorId(usuarioId).firstOrNull()
    }


    /**
     *   Remove a nossa tag que faz o user ficar logado fazendo assim que saimamos
     *    do app.
     *
     *    protected paras apenas aqueles nos quais herdare esta classe (ActivityBase) possam ter
     *    acesso.
     */
    protected suspend fun deslogaUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(usuarioLogadoPreferences)
        }
    }

    /**
     * Função que é criada para apontar para a tela de login
     * Usada caso o usuário não esteja logado
     *
     * adicionar a flag para nãoo acontecer o empilhamento de activities e termos comportamentos
     * inesperados
     */
    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish() // finaliza a tela de produtos!
    }

}