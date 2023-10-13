package com.example.namnam.data.network

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Ingredient
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {
    @GET("Category/GetCategories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("Ingredient/GetIngredients")
    suspend fun getAllIngredients(): Response<List<Ingredient>>
}