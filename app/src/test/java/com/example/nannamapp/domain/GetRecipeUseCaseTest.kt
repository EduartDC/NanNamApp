package com.example.nannamapp.domain

import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.UserPreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetRecipeUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: RecipesRepository
    lateinit var getRecipeUseCase : GetRecipeUseCase
    lateinit var idRecipe : String

    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        idRecipe = "3H3JZ8GIJJ"
        getRecipeUseCase = GetRecipeUseCase(idRecipe)
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.getRecipe(idRecipe) }

        //when
        val response =  getRecipeUseCase()

        //Then
        assert(response == 200)
    }

    @Test
    fun `when response is connection error then return 500`() = runBlocking{
        //Given
        coEvery { repository.getRecipe(idRecipe) }

        //when
        val response =  getRecipeUseCase()

        //Then
        assert(response == 500)
    }
}