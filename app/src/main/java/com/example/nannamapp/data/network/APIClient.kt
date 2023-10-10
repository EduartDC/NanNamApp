package com.example.namnam.data.network

import com.example.namnam.data.model.Category
import retrofit2.Response
import retrofit2.http.GET

interface APIClient {
    @GET("Category/GetCategories")
    suspend fun getAllCategories(): Response<List<Category>>
}