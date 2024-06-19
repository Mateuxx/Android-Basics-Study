package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.coroutineScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
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
                usuario
                    .filterNotNull() // só era fazer um collect quando esse usuario for diferente
                    // de null
                    .collect {
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