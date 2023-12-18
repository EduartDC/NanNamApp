package com.example.nannamapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://namnamapi.azurewebsites.net")
            //.baseUrl("https://namnamapi.azurewebsites.net/")

            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}