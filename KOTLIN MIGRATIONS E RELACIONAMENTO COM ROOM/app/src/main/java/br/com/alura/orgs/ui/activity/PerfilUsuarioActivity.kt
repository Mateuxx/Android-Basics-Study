package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.coroutineScope
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityPerfilUsuarioBinding
import kotlinx.coroutines.launch

class PerfilUsuarioActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activityPerfilUsuarioBotaoSairDoApp.setOnClickListener {
            lifecycle.coroutineScope.launch {
                deslogaUsuario()
            }
        }
    }

}