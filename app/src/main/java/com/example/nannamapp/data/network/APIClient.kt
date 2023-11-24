package com.example.namnam.data.network

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.NewRecipePost
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIClient {
    @GET("Category/GetCategories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("Ingredient/GetIngredients")
    suspend fun getAllIngredients(): Response<List<Ingredient>>

    @GET("Recipe/GetCookbook/{idUser}")
    suspend fun getCookBook(@Path("idUser")idUser: String): Response<List<Recipe>>

    @POST("Recipe/PostRecipe")
    suspend fun registerNewRecipe(@Body newRecipeDomain: NewRecipePost): Response<Void>

    @GET("Recipe/GetRecipe/{idRecipe}")
    suspend fun getRecipe(@Path("idRecipe")idRecipe : String) : Response<GetRecipeResponse>

    @GET("Recipe/GetRecipeList")
    suspend fun getRecipeList(): Response<List<Recipe>>

}