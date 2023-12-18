package com.example.nannamapp.data.network

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import com.example.nannamapp.data.model.GetPreferenceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IngredientService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllIngredients(): Pair<Int,List<Ingredient>> {
        return withContext(Dispatchers.IO) {
            var code = 0
            var body: List<Ingredient> = emptyList()
            try {
                val response = retrofit.create(APIClient::class.java).getAllIngredients()
                body = response.body()!!
                code = response.code()
                Pair(code,body)

            }catch (e : Exception)
            {
                code = 500;
                Pair(code,body)
            }

        }
    }
}