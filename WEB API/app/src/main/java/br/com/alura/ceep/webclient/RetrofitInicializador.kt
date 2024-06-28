package br.com.alura.ceep.webclient

import retrofit2.Retrofit

class RetrofitInicializador {

    // Instancia do retrofit
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .build()




}