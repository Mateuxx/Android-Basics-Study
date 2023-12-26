package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var TAG = "LOG DE: "

    // Propriedade de apoio score
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount
    /*
      Propriedade de apoio se pega fora do ViewModel a variável currentScrambledWord que é publica
      por padrão no Kotlin

     */
    private var wordsList: MutableList<String> = mutableListOf()
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
   * Re-initializes the game data to restart the game.
   */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    /*
   * Increases the game score if the player's word is correct.
   */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if(playerWord.equals(currentWord,true)) {
            increaseScore()
            return true
        }
        return false
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
            ++_currentWordCount
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