package com.example.nannamapp.domain

import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.ReviewRepository
import com.example.nannamapp.data.model.ReviewDomain

class SetReviewUseCase (private val newReview : ReviewDomain){
    private val repository = ReviewRepository()
    suspend operator fun invoke(): Int = repository.setReview(newReview)
}