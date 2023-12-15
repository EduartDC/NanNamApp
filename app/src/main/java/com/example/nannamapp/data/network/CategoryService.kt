package com.example.namnam.data.network


import com.example.namnam.data.model.Category
import com.example.nannamapp.core.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getCategories(): List<Category> {

            return withContext(Dispatchers.IO) {
                try {
                    val response = retrofit.create(APIClient::class.java).getAllCategories()
                    response.body() ?: emptyList()
                }catch (e : Exception){
                    emptyList()
                }

            }


    }
}