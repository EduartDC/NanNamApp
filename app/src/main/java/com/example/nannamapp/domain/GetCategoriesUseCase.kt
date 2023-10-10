package com.example.nannamapp.domain


import com.example.namnam.data.model.Category
import com.example.nannamapp.data.CategoryRepository

class GetCategoriesUseCase {
    private val repository = CategoryRepository()
    suspend operator fun invoke(): List<Category>? = repository.getAllcategories()
}