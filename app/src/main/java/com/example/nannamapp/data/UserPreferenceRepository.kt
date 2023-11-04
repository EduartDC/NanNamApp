package com.example.nannamapp.data

import com.example.nannamapp.data.model.ReviewProvider
import com.example.nannamapp.data.model.UserPreferenceProvider
import com.example.nannamapp.data.network.UserPreferenceService

class UserPreferenceRepository {
    private val api = UserPreferenceService()
    suspend fun getUserPreference(idUser : String) : Int{
        var response = api.getUserPreference(idUser)
        //RecipeProvider.recipeResponse.category = response.second.category
        UserPreferenceProvider.prefererence = response.second
        return response.first
    }

}