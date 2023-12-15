package com.example.nannamapp.domain

import com.example.nannamapp.data.ReviewRepository
import com.example.nannamapp.data.model.ReviewDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class EditReviewUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: ReviewRepository
    lateinit var setReviewsUseCase: EditReviewUseCase
    lateinit var newReview : ReviewDomain

    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        newReview = ReviewDomain(
            "ir1",
            "review desde test",
            2,
            "123",
        "r1")
        setReviewsUseCase = EditReviewUseCase()
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.editReview(newReview) }

        //when
        val response =  setReviewsUseCase(newReview)

        //Then
        assert(response == 200)
    }

    @Test
    fun `when response is connection error then return 500`() = runBlocking{
        //Given
        coEvery { repository.editReview(newReview) }

        //when
        val response =  setReviewsUseCase(newReview)

        //Then
        assert(response == 500)
    }
}