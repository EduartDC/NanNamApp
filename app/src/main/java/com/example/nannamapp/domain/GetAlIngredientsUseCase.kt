package com.example.nannamapp.domain

import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.data.IngredientRepository

class GetAlIngredientsUseCase {
    private val repository = IngredientRepository()
    suspend operator fun invoke(): Int = repository.getAllIngredients()
}