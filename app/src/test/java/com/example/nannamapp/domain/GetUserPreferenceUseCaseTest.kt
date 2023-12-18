package com.example.nannamapp.domain

import com.example.nannamapp.data.UserPreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetUserPreferenceUseCaseTest{
    @RelaxedMockK
    private lateinit var  repository: UserPreferenceRepository
    lateinit var getuserPreferenceUseCase : GetUserPreferenceUseCase


    @Before
    fun onBefore(){//preparacion de recursos para la prueba
        MockKAnnotations.init(this)
        getuserPreferenceUseCase = GetUserPreferenceUseCase("123")
    }

    @Test
    fun `when response is ok then return 200`() = runBlocking{
        //Given
        coEvery { repository.getUserPreference("123") }

        //when
        val response =  getuserPreferenceUseCase()

        //Then
        assert(response == 200)
    }

    @Test
    fun `when response is connection error then return 500`() = runBlocking{
        //Given
        coEvery { repository.getUserPreference("123") }

        //when
        val response =  getuserPreferenceUseCase()

        //Then
        assert(response == 500)
    }
}