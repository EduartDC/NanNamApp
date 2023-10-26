package com.example.nannamapp.domain

import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.model.GetRecipeResponse

class GetRecipeUseCase (private val idRecipe : String){
    private val repository = RecipesRepository()
    suspend operator fun invoke() : Pair<Int,GetRecipeResponse> = repository.getRecipe(idRecipe)
}