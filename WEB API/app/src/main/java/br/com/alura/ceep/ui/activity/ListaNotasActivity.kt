package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityListaNotasBinding
import br.com.alura.ceep.extensions.vaiPara
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.alura.ceep.webclient.RetrofitInicializador
import kotlinx.coroutines.launch
import retrofit2.Call

class ListaNotasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaNotasBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }
    private val dao by lazy {
        AppDatabase.instancia(this).notaDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscaNotas()
            }
        }
        //Executa a requisição -> Buscar todas as notas
        val call: Call<List<Nota>> = RetrofitInicializador().notaService.buscaTodas()
        //por meio do call.execute() nos temos acesso a resposta dessa requesição
        val resposta = call.execute()
        //Queremos ver o body da requisição que seria basicamente o arquivo.jason
        resposta.body()?.let { notas ->
            Log.i("ListaNOtas", "onCreate: $notas")
            
        }
    }

    /**
     * Botão para chamar a activity "FormNotaActivity" quando clicado
     */
    private fun configuraFab() {
        binding.activityListaNotasFab.setOnClickListener {
            Intent(this, FormNotaActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    /**
     * RecyclerView Stuff
     */
    private fun configuraRecyclerView() {
        binding.activityListaNotasRecyclerview.adapter = adapter
        adapter.quandoClicaNoItem = { nota ->
            vaiPara(FormNotaActivity::class.java) {
                putExtra(NOTA_ID, nota.id)
            }
        }
    }

    private suspend fun buscaNotas() {
        dao.buscaTodas()
            .collect { notasEncontradas ->
                binding.activityListaNotasMensagemSemNotas.visibility =
                    if (notasEncontradas.isEmpty()) {
                        binding.activityListaNotasRecyclerview.visibility = GONE
                        VISIBLE
                    } else {
                        binding.activityListaNotasRecyclerview.visibility = VISIBLE
                        adapter.atualiza(notasEncontradas)
                        GONE
                    }
            }
    }
}