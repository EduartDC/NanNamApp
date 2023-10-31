package com.example.nannamapp.data

import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.data.model.ReviewProvider
import com.example.nannamapp.data.network.ReviewService

class ReviewRepository {
    private val api = ReviewService()

    suspend fun getReview(idRecipe : String) : Int{
        var response = api.getReview(idRecipe)
        //RecipeProvider.recipeResponse.category = response.second.category
        ReviewProvider.reviews = response.second
        println("resrvice"+ response.second[0].idReview)
        return response.first
    }

    suspend fun setReview(newReview : ReviewDomain): Int{
        var response = api.setReview(newReview)
        return response
    }

}