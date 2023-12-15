package com.example.nannamapp.data


import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.data.model.NewRecipeDomain
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.data.network.RecipesService

class RecipesRepository {
    private val api = RecipesService()

    suspend fun getCookBook(idUser: String): Int {
        val response = api.getCookBook(idUser)
        RecipeProvider.cookBook = response.second
        return response.first
    }

    suspend fun pushRecipe(newRecipe: NewRecipeDomain): Int {
        var response = api.pushRecipe(newRecipe)
        return response
    }

    suspend fun getRecipe(idRecipe: String): Int {
        var response = api.getRecipe(idRecipe)
        RecipeProvider.recipeResponse.recipe = response.second.recipe
        RecipeProvider.recipeResponse.stepList = response.second.stepList
        RecipeProvider.recipeResponse.ingredientList = response.second.ingredientList
        RecipeProvider.recipeResponse.ingredientAmounList = response.second.ingredientAmounList
        RecipeProvider.recipeResponse.nutritionalDataList = response.second.nutritionalDataList
        return response.first
    }

    suspend fun editRecipe(newRecipe : NewRecipePost): Int{
        var response = api.editRecipe(newRecipe)
        return response
    }
    suspend fun getRecipesList(): List<Recipe>{
        val response = api.getRecipesList()
        RecipeProvider.recipeList = response
        return response
    }
    suspend fun getRecipesListByCategory(idCategory : String): List<Recipe>{
        val response = api.getRecipesListByCategory(idCategory)
        RecipeProvider.recipeList = response
        return response
    }

    suspend fun getCategoryList(): List<Category>{
        var response = mutableListOf<Category>()
        var category = Category("1", "todos")
        response.add(category)

        val responseApi = api.getCategoryList()
        response.addAll(responseApi)
        return response
    }

}