package com.example.namnam.data.network

import com.example.namnam.core.RetrofitHelper
import com.example.namnam.data.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getcategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(APIClient::class.java).getAllCategories()
            response.body() ?: emptyList()
        }
    }
}