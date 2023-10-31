package com.example.nannamapp.domain

import com.example.nannamapp.data.ReviewRepository

class GetReviewsUseCase (private val idReview : String) {
    private val repository = ReviewRepository()

    suspend operator fun invoke() : Int = repository.getReview(idReview)
}