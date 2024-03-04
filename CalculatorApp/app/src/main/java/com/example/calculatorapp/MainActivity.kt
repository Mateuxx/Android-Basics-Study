package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Estudar sobre o SetContentView please!
        val button1 = binding.button7
        button1.setOnClickListener {
            binding.resultView.text = "idont know"
            Log.d("TAG", "onCreate: ")
        }
    }





}