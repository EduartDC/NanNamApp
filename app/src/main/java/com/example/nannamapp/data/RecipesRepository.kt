package com.example.nannamapp.data

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.Recipe
import com.example.namnam.data.network.CategoryService
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.data.network.RecipesService

class RecipesRepository {
    private val api = RecipesService()

    suspend fun getCookBook(idUser: String): List<Recipe> {
        val response = api.getCookBook(idUser)
        RecipeProvider.cookBook = response
        return response
    }
}