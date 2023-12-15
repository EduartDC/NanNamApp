package com.example.nannamapp.domain

import com.example.nannamapp.data.RecipesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetCookBookUseCaseTest{
    @RelaxedMockK
    lateinit var repository: RecipesRepository
    private lateinit var getCookBookUseCase : GetCookBookUseCase
    lateinit var idUser : String


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        idUser = "123"
        getCookBookUseCase = GetCookBookUseCase(idUser)
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.getCookBook(idUser) }

        //when
        val response =  getCookBookUseCase()

        //Then
        assert(response == 200)
    }
    @Test
    fun `when response is ok then return 404`() = runBlocking{
        //Given
        coEvery { repository.getCookBook(idUser) }

        //when
        val response =  getCookBookUseCase()

        //Then
        assert(response == 404)
    }

    @Test
    fun `when response is ok then return 500`() = runBlocking{
        //Given
        coEvery { repository.getCookBook(idUser) }

        //when
        val response =  getCookBookUseCase()

        //Then
        assert(response == 500)
    }


}