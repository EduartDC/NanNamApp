package com.example.nannamapp.data.model

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CookinginstructionDomain
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.RecipeDomain

data class GetRecipeResponse (

    var recipe : RecipeDomain = RecipeDomain("", "", "", "", "","",11,""),
    var category : Category = Category("",""),
    var stepList : List<CookinginstructionDomain> = emptyList(),
    var ingredientList : List<Ingredient> = emptyList(),
    var ingredientAmounList : List<RecipeHasIngredient> = emptyList(),
    var nutritionalDataList : List<NutritionalDataDomain> = emptyList()
)