package br.com.alura.ceep.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.R
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityFormNotaBinding
import br.com.alura.ceep.extensions.tentaCarregarImagem
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.dialog.FormImagemDialog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FormNotaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormNotaBinding.inflate(layoutInflater)
    }

    //Recurso mais reativo para armazenar as Imagens!! Abordar esse estilo!
    private var imagem: MutableStateFlow<String?> = MutableStateFlow(null)

    private val dao by lazy {
        AppDatabase.instancia(this).notaDao()
    }
    private var notaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.activityFormNotaToolbar)
        configuraImagem()
        tentaCarregarIdDaNota()
        lifecycleScope.launch {
            launch {
                tentaBuscarNota()
            }
            launch {
                configuraCarregamentoDeImagem()
            }
        }

    }

    private suspend fun configuraCarregamentoDeImagem() {
        val imagemNota = binding.activityFormNotaImagem
        imagem.collect { imagemNova ->
            imagemNota.visibility =
                if (imagemNova.isNullOrBlank())
                    GONE
                else {
                    imagemNota.tentaCarregarImagem(imagemNova)
                    VISIBLE
                }
        }
    }

    private suspend fun tentaBuscarNota() {
        notaId?.let {id ->
            dao.buscaPorId(id)
                .filterNotNull()
                .collect { notaEncontrada ->
                    notaId = notaEncontrada.id
                    imagem.value = notaEncontrada.imagem
                    binding.activityFormNotaTitulo.setText(notaEncontrada.titulo)
                    binding.activityFormNotaDescricao.setText(notaEncontrada.descricao)
                }
        }
    }

    /**
     * Pegar a extra(dados adicionais no qual podem ser anexados a uma intente) de uma intent
     * e a a passando como valor com o ID correto passando
     */
    private fun tentaCarregarIdDaNota() {
        notaId = intent.getStringExtra(NOTA_ID)
    }

    /**
     * chama o dialog para carregar a imagem
     */
    private fun configuraImagem() {
        binding.activityFormNotaAdicionarImagem.setOnClickListener {
            FormImagemDialog(this)
                .mostra(imagem.value) { imagemCarregada ->
                    binding.activityFormNotaImagem
                        .tentaCarregarImagem(imagemCarregada)
                    imagem.value = imagemCarregada
                }
        }
    }

    /**
     * Criação do menu da tela de listas
     * Adicionar e remover
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_nota_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Acções dos menuns
      */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.form_nota_menu_salvar -> {
                salva()
            }

            R.id.form_nota_menu_remover -> {
                remove()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Exclui a nota do BD
     */
    private fun remove() {
        lifecycleScope.launch {
            notaId?.let { id ->
                dao.remove(id)
            }
            finish()
        }
    }

    /**
     * Salve a nota
     */
    private fun salva() {
        val nota = criaNota()
        lifecycleScope.launch {
            dao.salva(nota)
            finish()
        }
    }

    /**
     * Cria a nota e salva no Database
     */
    private fun criaNota(): Nota {
        val titulo = binding.activityFormNotaTitulo.text.toString()
        val descricao = binding.activityFormNotaDescricao.text.toString()
        return notaId?.let { id ->
            Nota(
                id = id,
                titulo = titulo,
                descricao = descricao,
                imagem = imagem.value
            )
        } ?: Nota( //Caso seja numo receber isso aqui
            titulo = titulo,
            descricao =  descricao,
            imagem = imagem.value
        )
    }

}
