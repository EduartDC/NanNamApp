package com.example.nannamapp.data

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.IngredientProvider
import com.example.namnam.data.network.CategoryService
import com.example.nannamapp.data.network.IngredientService

class IngredientRepository {
    private val api = IngredientService()
    suspend fun getAllIngredients(): Int {
        val response = api.getAllIngredients()
        IngredientProvider.ingredients = response.second
        return response.first
    }
}