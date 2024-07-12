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
import br.com.alura.ceep.webclient.NotaWebClient
import br.com.alura.ceep.webclient.RetrofitInicializador
import br.com.alura.ceep.webclient.model.NotaResposta
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val webClient by lazy {
        NotaWebClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
        lifecycleScope.launch {
            val notas = webClient.buscaTodas()
            Log.i("ListaNotas", "onCreate: retrofit coroutines $notas")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscaNotas()
            }
        }

        //retrofitSemCourotines()

    }

    private fun retrofitSemCourotines() {
      //  val call: Call<List<NotaResposta>> = RetrofitInicializador().notaService.buscaTodas()
        //Thread paralela
        //        lifecycleScope.launch(IO) {
        //            //Executa a requisição -> Buscar todas as notas
        //
        //            /**
        //             * por meio do call.execute() nos temos acesso a resposta dessa requesição
        //             * É uma requesição sincronna -> Trava a trhead principal e ela só funciona a funcionar
        //             * quando ela essa call terminar de ser executada
        //             */
        //            val resposta = call.execute() //Trava a thread principal
        //            //Queremos ver o body da requisição que seria basicamente o arquivo.jason
        //            resposta.body()?.let { notasResposta ->
        //                val notas: List<Nota> = notasResposta.map {
        //                    it.nota //Chama a property de conversão para os valores dos que a gente quer internamente
        //                }
        //                Log.i("ListaNOtas", "onCreate: $notas")
        //
        //            }
        //        }

//        /**
//         * Não necessita de uma thread paralela para executar ele ja tem isso na sua implementação
//         * No fim da sua execução ele volta para um desses dois metodos abaixo
//         */
//        call.enqueue(object : Callback<List<NotaResposta>?> {
//            //Devolve a resposta pra gente
//            override fun onResponse(
//                call: Call<List<NotaResposta>?>,
//                resposta: Response<List<NotaResposta>?>
//            ) {
//                resposta.body()?.let { notasResposta ->
//                    val notas: List<Nota> = notasResposta.map {
//                        it.nota //Chama a property de conversão para os valores dos que a gente quer internamente
//                    }
//                    Log.i("ListaNOtas", "onCreate: $notas")
//
//                }
//            }
//
//            //Para qualquer eventual erro na comunicação
//            override fun onFailure(call: Call<List<NotaResposta>?>, t: Throwable) {
//                Log.e("ListaNotas", "onFailure: ", t)
//            }
//        })
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