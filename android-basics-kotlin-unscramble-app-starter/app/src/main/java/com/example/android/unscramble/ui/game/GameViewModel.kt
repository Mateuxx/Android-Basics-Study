package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var TAG = "LOG DE: "

    // Propriedade de apoio score
    private var _score = 0
    val score: Int
        get() = _score

    private var currentWordCount = 0
    /*
      Propriedade de apoio se pega fora do ViewModel a variável currentScrambledWord que é publica
      por padrão no Kotlin
     */
    private lateinit var _currentScrambledWord: String

    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordslist: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String



    init {
        Log.d("GameFragment", "GameViewmodel Created!!! ")
        getNextWord()
    }

    /*
   Metodo para pegar a proxima paralavra embaralhada
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        Log.d(TAG, "Tempoword: $tempWord")
        //Embaralha a palavra escolhida que está em um array
        tempWord.shuffle()

        // Caso a palavra embaralhada seja a mesma, embaralhe o vetor novamente!!
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        // Verificação para não repetir as palavras que ja foram utilizadas
        if (wordslist.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++currentWordCount
            wordslist.add(currentWord)
        }
    }

    /*
* Returns true if the current word count is less than MAX_NO_OF_WORDS.
* Updates the next word.
*/
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}