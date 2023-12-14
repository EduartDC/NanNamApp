package com.example.nannamapp.domain

import com.example.namnam.data.model.Recipe
import com.example.nannamapp.data.RecipesRepository

class GetCookBookUseCase(private val idUser : String) {
    private val repository = RecipesRepository()

    suspend operator fun invoke(): Int = repository.getCookBook(idUser)
}