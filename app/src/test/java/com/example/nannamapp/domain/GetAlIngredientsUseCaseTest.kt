package com.example.nannamapp.domain

import com.example.nannamapp.data.IngredientRepository
import com.example.nannamapp.data.ReviewRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetAlIngredientsUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: IngredientRepository
    lateinit var getAllIngredientsUseCase: GetAlIngredientsUseCase

    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        getAllIngredientsUseCase = GetAlIngredientsUseCase()
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.getAllIngredients() }

        //when
        val response =  getAllIngredientsUseCase()

        //Then
        assert(response == 200)
    }

    @Test
    fun `when response is connection error then return 500`() = runBlocking{
        //Given
        coEvery { repository.getAllIngredients() }

        //when
        val response =  getAllIngredientsUseCase()

        //Then
        assert(response == 200)
    }
}