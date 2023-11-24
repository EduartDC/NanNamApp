package com.example.nannamapp.domain

import com.example.namnam.data.model.Recipe
import com.example.nannamapp.data.RecipesRepository

class GetRecipeListUseCase {
    private val repository = RecipesRepository()
    suspend operator fun invoke(): List<Recipe>? = repository.getRecipesList()
}