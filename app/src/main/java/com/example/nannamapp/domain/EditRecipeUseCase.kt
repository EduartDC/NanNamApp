package com.example.nannamapp.domain

import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.model.NewRecipePost

class EditRecipeUseCase(private val newRecipe: NewRecipePost) {
    private val repository = RecipesRepository()
    suspend operator fun invoke(): Int = repository.editRecipe(newRecipe)
}