package com.example.nannamapp.domain

import com.example.namnam.data.model.Recipe
import com.example.nannamapp.data.RecipesRepository

class GetCookBookUseCase(var idUser : String) {
    private val repository = RecipesRepository()

    suspend operator fun invoke(): List<Recipe>? = repository.getCookBook(idUser)
}