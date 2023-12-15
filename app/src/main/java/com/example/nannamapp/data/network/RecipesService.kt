package com.example.nannamapp.data.network

import android.util.Log
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Recipe
import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.NewRecipeDomain
import com.example.nannamapp.data.model.NewRecipePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getCookBook(idUser: String): Pair<Int, List<Recipe>>{
        return withContext(Dispatchers.IO) {
            var code : Int = 0
            var body = emptyList<Recipe>()
            try {
                val response = retrofit.create(APIClient::class.java).getCookBook(idUser)
                if (response.isSuccessful) {
                    code = response.code()
                    body = response.body() ?: emptyList()
                }
                else{
                    code = response.code()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                code = 500

            }
            Pair(code, body)
        }

    }
    suspend fun pushRecipe(newRecipe : NewRecipeDomain): Int {
        return withContext(Dispatchers.IO) {
            var code : Int = 0
            Log.d("DENTRO DE SERVICE" ,"SI")
            try {
                val response = retrofit.create(APIClient::class.java).registerNewRecipe(newRecipe)
                // response.errorBody()?.string()?.let { Log.d("Mensaje de la api", it) }
                println("MENSAJE DEL API CREATE: " + response.body())
                if (response.isSuccessful)
                    code = 200
            } catch (e: Exception) {
                e.printStackTrace()
                code = 500
            }
            code
        }
    }
    suspend fun getRecipe(idRecipe : String): Pair<Int, GetRecipeResponse>{
        return withContext(Dispatchers.IO) {
            var code = 0
            var body = GetRecipeResponse()
            try {
                val response = retrofit.create(APIClient::class.java).getRecipe(idRecipe)
                if (response.isSuccessful) {
                    code = response.code()
                    body = response.body() ?: GetRecipeResponse()
                }
                else{
                    code = response.code()
                }
            }catch (e : Exception){
                code = 500
            }
            Pair(code, body)
        }
    }
    suspend fun editRecipe(newRecipe : NewRecipePost): Int {
        return withContext(Dispatchers.IO) {
            var code : Int = 0
            try {

                val response = retrofit.create(APIClient::class.java).updateRecipe(newRecipe)
                if (response.isSuccessful) {

                    code = 200
                }
                else{
                    code = 400

                }
            } catch (e: Exception) {
                e.printStackTrace()
                code = 500
            }
            code
        }
    }
    suspend fun getRecipesList(): List<Recipe>{
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(APIClient::class.java).getRecipeList()
                response.body() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun getRecipesListByCategory(idCategory: String): List<Recipe>{
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(APIClient::class.java).getRecipeListByCategory(idCategory)
                response.body() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
    suspend fun getCategoryList(): List<Category>{
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(APIClient::class.java).getAllCategories()
                response.body() ?: emptyList()

            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}