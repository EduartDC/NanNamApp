package com.example.nannamapp.domain

import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.ReviewRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetReviewsUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: ReviewRepository
    lateinit var getReviewsUseCase : GetReviewsUseCase
    lateinit var idRecipe : String

    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        idRecipe = "3H3JZ8GIJJ"
        getReviewsUseCase = GetReviewsUseCase(idRecipe)
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.getReview(idRecipe) }

        //when
        val response =  getReviewsUseCase()

        //Then
        assert(response == 200)
    }

    @Test
    fun `when response is connection error then return 500`() = runBlocking{
        //Given
        coEvery { repository.getReview(idRecipe) }

        //when
        val response =  getReviewsUseCase()

        //Then
        assert(response == 500)
    }
}