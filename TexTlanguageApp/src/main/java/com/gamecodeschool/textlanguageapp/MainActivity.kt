package com.gamecodeschool.textlanguageapp

import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.textclassifier.TextClassification
import android.view.textclassifier.TextClassificationManager
import android.view.textclassifier.TextClassifier
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity() : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var detectButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var  textClassification: TextClassification
    private lateinit var textClassifier: TextClassifier

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textClassificationManager = getSystemService(
            TextClassificationManager::class.java
        )
        if (textClassificationManager != null) {
             textClassifier = textClassificationManager.textClassifier
        }


        //Setando as views de cada componete de ui
        editText = findViewById(R.id.editText)
        detectButton = findViewById(R.id.detectButton)
        resultTextView = findViewById(R.id.resultTextView)

        detectButton.setOnClickListener{
            val textToDetect = editText.text.toString()
            detectLanguage(textToDetect)
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun detectLanguage(text: String) {

        val someText = "This sentence is commonly used for various purposes, such as testing fonts, keyboards, or text recognition, because it includes every letter of the English alphabet at least once"
        val request = TextClassification.Request.Builder(someText,0,someText.length).build()
        Log.d("LOG", "onCreate:  $request")

        val result = textClassifier.classifyText(someText,0,someText.length-1, LocaleList.getDefault())
        Log.d("LOG", "onCreate:  $result")
    }
}
