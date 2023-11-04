package com.example.nannamapp.domain

import com.example.nannamapp.data.UserPreferenceRepository

class GetUserPreferenceUseCase (private val idUser : String){
    private val repository = UserPreferenceRepository()

    suspend operator fun invoke() : Int = repository.getUserPreference(idUser)
}