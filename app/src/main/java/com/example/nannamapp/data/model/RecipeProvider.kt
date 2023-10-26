package com.example.nannamapp.data.model

import com.example.namnam.data.model.RecipeDomain

class RecipeProvider {
    companion object{
        var cookBook: List<RecipeDomain> = emptyList()
        var recipeResponse : GetRecipeResponse = GetRecipeResponse()
    }
}