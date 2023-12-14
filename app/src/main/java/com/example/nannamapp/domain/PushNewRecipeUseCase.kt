package com.example.nannamapp.domain

import com.example.namnam.data.model.Category
import com.example.nannamapp.data.CategoryRepository
import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.model.NewRecipeDomain
import com.example.nannamapp.data.model.NewRecipePost

 class PushNewRecipeUseCase (private val newRecipe: NewRecipeDomain){
    private val repository = RecipesRepository()
    suspend operator fun invoke(): Int = repository.pushRecipe(newRecipe)
}