package com.example.nannamapp.data.model

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CookinginstructionDomain
import com.example.namnam.data.model.RecipeDomain

data class NewRecipePost (
    var recipeDomain : RecipeDomain,
    var instructions : List<CookinginstructionDomain> = emptyList(),
    var category : Category,
    var recipeHasIngredients : List<RecipeHasIngredient> = emptyList(),
)