package com.example.nannamapp.data

import com.example.namnam.data.model.RecipeDomain
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.data.network.RecipesService

class RecipesRepository {
    private val api = RecipesService()

    suspend fun getCookBook(idUser: String): List<RecipeDomain> {
        val response = api.getCookBook(idUser)
        RecipeProvider.cookBook = response
        return response
    }

    suspend fun pushRecipe(newRecipe : NewRecipePost): Int{
        var response = api.pushRecipe(newRecipe)
        return response
    }

    suspend fun getRecipe(idRecipe : String) : Int{
        var response = api.getRecipe(idRecipe)
        RecipeProvider.recipeResponse.recipe = response.second.recipe
        RecipeProvider.recipeResponse.stepList = response.second.stepList
        RecipeProvider.recipeResponse.ingredientList = response.second.ingredientList
        //RecipeProvider.recipeResponse.category = response.second.category
        RecipeProvider.recipeResponse.ingredientAmounList = response.second.ingredientAmounList
        return response.first
    }
}