package com.example.nannamapp.data

import android.util.Log
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider

import com.example.namnam.data.network.CategoryService

class CategoryRepository {
    private val api = CategoryService()
    suspend fun getAllcategories(): List<Category> {
        val response = api.getcategories()
        CategoryProvider.categories = response
        return response
    }
}