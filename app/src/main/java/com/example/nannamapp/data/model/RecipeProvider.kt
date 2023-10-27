package com.example.nannamapp.data.model

import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.Recipe

class RecipeProvider {
    companion object{
        var cookBook: List<Recipe> = emptyList()
        var recipeResponse : GetRecipeResponse = GetRecipeResponse()
    }
}