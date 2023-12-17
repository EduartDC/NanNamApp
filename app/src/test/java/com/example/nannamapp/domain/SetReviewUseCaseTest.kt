package com.example.nannamapp.domain

import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CookinginstructionDomain
import com.example.namnam.data.model.RecipeDomain
import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.ReviewRepository
import com.example.nannamapp.data.model.NewRecipeDomain
import com.example.nannamapp.data.model.RecipeHasIngredient
import com.example.nannamapp.data.model.ReviewDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class SetReviewUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: ReviewRepository
    lateinit var setReviewsUseCase: SetReviewUseCase
    //objetos a enviar
    lateinit var newReview : ReviewDomain

    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        newReview = ReviewDomain(
            "",
            "review desde test 2",
            2,
            "123",
            "r1"
        )

        setReviewsUseCase = SetReviewUseCase()
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.setReview(newReview) }

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