package br.com.alura.orgs.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Inicia uma nova activity de forma mais flexível
 * @param clazz:  O primeiro parâmetro é a classe da atividade que você deseja iniciar.
 * O tipo Class<*> indica que pode ser qualquer classe.
 * Essa função de extensão é útil porque simplifica a criação e configuração de intents,
 * tornando o código mais legível e conciso.
 *
 */
fun Context.vaiPara(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}

fun Context.toast(mensagem: String) {
    Toast.makeText( //Caso falhe a autenticação joga esse toast
        this,
        mensagem,
        Toast.LENGTH_SHORT
    ).show()
}