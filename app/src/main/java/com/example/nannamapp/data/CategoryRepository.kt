package com.example.nannamapp.data

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider

import com.example.namnam.data.network.CategoryService

class CategoryRepository {
    private val api = CategoryService()
    suspend fun getAllcategories(): List<Category> {
        val response = api.getCategories()
        CategoryProvider.categories = response
        return response
    }
}