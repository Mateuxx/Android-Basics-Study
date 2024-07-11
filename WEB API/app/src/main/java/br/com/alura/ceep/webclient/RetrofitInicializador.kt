package br.com.alura.ceep.webclient

import br.com.alura.ceep.webclient.services.NotaService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class RetrofitInicializador {

    // Instancia do retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.104:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val notaService: NotaService = retrofit.create(NotaService::class.java)


}