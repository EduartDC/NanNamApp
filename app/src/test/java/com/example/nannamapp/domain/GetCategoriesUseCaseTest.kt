package com.example.nannamapp.domain

import com.example.nannamapp.data.CategoryRepository
import com.example.nannamapp.data.IngredientRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetCategoriesUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: CategoryRepository
    lateinit var getCategoriesUseCase:GetCategoriesUseCase

    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        getCategoriesUseCase = GetCategoriesUseCase()
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.getAllcategories() }

        //when
        val response =  getCategoriesUseCase()

        //Then
        assert(response?.size.toString().toInt() > 0)
    }

    @Test
    fun `when response is connection error then return 500`() = runBlocking{
        //Given
        coEvery { repository.getAllcategories() }

        //when
        val response =  getCategoriesUseCase()

        //Then
        assert(response?.size.toString().toInt() == 0)
    }
}