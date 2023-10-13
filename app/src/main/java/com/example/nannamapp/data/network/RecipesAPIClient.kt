package com.example.nannamapp.data.network

import com.example.namnam.data.model.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesAPIClient {
    @GET("Recipes/GetCookbook{idUser}")
    suspend fun getCookBook(@Path("idUser")idUser: String): Response<List<Recipe>>
}
