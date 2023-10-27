package com.example.nannamapp.data.network

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IngredientService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllIngredients(): Pair< List<Ingredient>,Int> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(APIClient::class.java).getAllIngredients()
            val ingredients =     response.body() ?: emptyList()
            val httpCode = response.code()
            Pair(ingredients,httpCode)
        }
    }
}