package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycle.coroutineScope.launch {
            //Ter informação no qual a gente salvou, no qual todos consguem acessar essa informação
            // do nosso dataStore
            launch {
                /**
                 * Ler os dados no quais estão no data store se tiver o usuario no flow ou seja,
                 * o usuaŕio ja está conectado, se não tiver logado (id for nulo) ele vai para
                 * a tela de login!!!
                 */
                verificaUsuarioLogado()
            }
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

    private fun buscaUsuario(usuarioId: String) {

        lifecycleScope.launch {
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
            usuarioDao.buscaPorId(usuarioId).firstOrNull()?.let {
                launch {
                    buscaProdutosUsuario()
                }
            }
        }
    }

    private fun CoroutineScope.buscaProdutosUsuario() {
        launch {
            produtoDao.buscaTodos().collect { produtos ->
                adapter.atualiza(produtos)
            }
        }
    }

    /**
     * Criação do menu para "Sair do app"
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     *Ação para sair do app caso criado, no caso temos que remover a tag que passamos para ela remover
     * do nosso datastore, lembrando que cada ação dentro do DataStore deve ser feita dentro do nosso
     * um escope routine
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_lista_produtos_sair_app -> {
                lifecycle.coroutineScope.launch {
                    deslogaUsuario()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     *   Remove a nossa tag que faz o user ficar logado fazendo assim que saimamos
     *    do app.
     */
    private suspend fun deslogaUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(usuarioLogadoPreferences)
        }
    }

    /**
     * Função que é criada para apontar para a tela de login
     * Usada caso o usuário não esteja logado
     */
    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java)
        finish() // finaliza a tela de produtos!
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }


}