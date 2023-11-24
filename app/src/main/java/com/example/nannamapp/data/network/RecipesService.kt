package com.example.nannamapp.data.network

import android.util.Log
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Recipe
import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.NewRecipePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getCookBook(idUser: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(APIClient::class.java).getCookBook(idUser)
                response.body() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
    suspend fun pushRecipe(newRecipe : NewRecipePost): Int {
        return withContext(Dispatchers.IO) {
            var code : Int = 0
            Log.d("DENTRO DE SERVICE" ,"SI")
            try {
                val response = retrofit.create(APIClient::class.java).registerNewRecipe(newRecipe)
                // response.errorBody()?.string()?.let { Log.d("Mensaje de la api", it) }
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
                code = response.code()
                body = response.body() ?: GetRecipeResponse()
                Pair(code,body)
            }catch (e : Exception){
                code = 500
                Pair(code,body)
            }

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
}