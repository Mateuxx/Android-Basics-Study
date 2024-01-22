package com.gamecodeschool.mlkitlanguagedetector

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "LangIDActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var detectButton: Button
    private lateinit var resultTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        detectButton = findViewById(R.id.detectButton)
        resultTextView = findViewById(R.id.resultTextView)


        detectButton.setOnClickListener{
            val textToDetect = editText.text.toString()
            Log.d(TAG, "textToDetectValue: $textToDetect")
            lifecycleScope.launch(Dispatchers.Main) {
                val result = runLanguageIdentifier(textToDetect)
                Log.d(TAG, "RESULT: $result")

                // Faça algo com o resultado, por exemplo, exibir no TextView
                resultTextView.text = "Domain language: ${getDomainLanguage(result)}"
            }
            getPossibleLanguuages(textToDetect)
        }
    }

    suspend fun runLanguageIdentifier(input: String): String {
        val deferredResult = CompletableDeferred<String>()
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(input)
            .addOnSuccessListener { languageCode ->
                deferredResult.complete(languageCode)
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
                deferredResult.complete(it.toString())
                Log.e("TAG", "Model couldn’t be loaded or other internal error", )
            }
        return deferredResult.await()
    }

    fun getPossibleLanguuages(text: String) {
        // [START get_possible_languages]
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyPossibleLanguages(text)
            .addOnSuccessListener { identifiedLanguages ->
                for (identifiedLanguage in identifiedLanguages) {
                    val language = identifiedLanguage.languageTag
                    val confidence = identifiedLanguage.confidence
                    Log.i(TAG, "getPossibleLanguuages Values: $language $confidence")
                }
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
                // ...
            }
        // [END get_possible_languages]
    }

    fun getDomainLanguage(input: String): String {
        return when(input){
            "en" -> "English"
            "pt" -> "Portuguese"
            else -> "Cant identify a language"
        }
    }
}
