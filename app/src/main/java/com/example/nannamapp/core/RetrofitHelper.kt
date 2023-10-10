package com.example.nannamapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5093")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}