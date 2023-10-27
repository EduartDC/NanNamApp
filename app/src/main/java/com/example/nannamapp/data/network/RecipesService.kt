package com.example.nannamapp.data.network

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Recipe
import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
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

}