package com.example.namnam.data.network

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.data.model.GetPreferenceResponse
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.JsonResult
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.data.model.NewRecipeDomain
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.ReviewDomain

import com.example.nannamapp.data.model.SetPreferenceResponse

import com.example.nannamapp.data.model.User

import com.google.gson.Gson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIClient {
    @GET("Category/GetCategories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("Ingredient/GetIngredients")
    suspend fun getAllIngredients(): Response<List<Ingredient>>

    @GET("Recipe/GetCookbook/{idUser}")
    suspend fun getCookBook(@Path("idUser")idUser: String): Response<List<Recipe>>

    @POST("Recipe/PostRecipe")
    suspend fun registerNewRecipe(@Body newRecipeDomain: NewRecipeDomain): Response<Void>

    @GET("Recipe/GetRecipe/{idRecipe}")
    suspend fun getRecipe(@Path("idRecipe")idRecipe : String) : Response<GetRecipeResponse>

    @GET("Review/GetReview/{idRecipe}")
    suspend fun getReview(@Path("idRecipe")idRecipe : String) : List<ReviewDomain>

    @POST("Review/setReview")
    suspend fun setReview(@Body newReview: ReviewDomain): Response<Void>

    @POST("Review/EditReview")
    suspend fun editReview(@Body newReview: ReviewDomain): Response<Void>

    @GET("Category/GetUserPreferences/{idUser}")
    suspend fun getUserPreference(@Path("idUser")idUser: String): Response<GetPreferenceResponse>

    @POST("Category/SetUserPreference")
    suspend fun setUserPreference(@Body setUserPreferenceResponse: SetPreferenceResponse): Response<Void>

  
    @PUT("Recipe/UpdateRecipe")
    suspend fun updateRecipe(@Body newRecipeDomain: NewRecipePost): Response<Void>
  
    @POST("Login/LoginUser")
    suspend fun loginUser(@Body loginCredentials: Login): Response<JsonResult>
  
    @POST("Login/RegisterUser")
    suspend fun registerUser(@Body registerCredentials: User): Response<String>


}