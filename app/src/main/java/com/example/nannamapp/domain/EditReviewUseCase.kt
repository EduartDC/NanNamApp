package com.example.nannamapp.domain

import com.example.nannamapp.data.ReviewRepository
import com.example.nannamapp.data.model.ReviewDomain

class EditReviewUseCase {
    private val repository = ReviewRepository()

    suspend operator fun invoke(newReview: ReviewDomain): Int = repository.editReview(newReview)
}