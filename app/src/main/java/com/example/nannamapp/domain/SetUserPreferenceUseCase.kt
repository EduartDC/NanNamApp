package com.example.nannamapp.domain

import com.example.nannamapp.data.ReviewRepository
import com.example.nannamapp.data.UserPreferenceRepository
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.data.model.SetPreferenceResponse

class SetUserPreferenceUseCase {
    private val repository = UserPreferenceRepository()

    suspend operator fun invoke(setUserPreferenceResponse: SetPreferenceResponse): Int = repository.setUserPreferenceResponse(setUserPreferenceResponse)
}