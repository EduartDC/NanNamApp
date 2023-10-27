package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.domain.GetAlIngredientsUseCase
import kotlinx.coroutines.launch

class IngredientViewModel : ViewModel() {
    val ingredientModel = MutableLiveData<Ingredient>()
    val httpCode : Int = 0
    var getAllIngredients = GetAlIngredientsUseCase()
    //comentario de prueba
    fun onCreate() {
        viewModelScope.launch {
            val result = getAllIngredients()
            val ingredients: List<Ingredient>? = result.first
            if (ingredients != null && ingredients.isNotEmpty()) {
                ingredientModel.postValue(ingredients[0])
            }
        }
    }
}