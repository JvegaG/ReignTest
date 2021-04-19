package com.example.reigntest.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val url = "https://hn.algolia.com/api/v1/"

    fun getService(): Retrofit = Retrofit.Builder()
    .baseUrl(url)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val dataFromService: ServiceHackNews = getService().create(ServiceHackNews::class.java)
}