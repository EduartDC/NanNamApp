package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.domain.GetAlIngredientsUseCase
import kotlinx.coroutines.launch

class IngredientViewModel : ViewModel() {
    val ingredientModel = MutableLiveData<Ingredient>()
    var getAllIngredients = GetAlIngredientsUseCase()
    //comentario de prueba
    fun onCreate() {
        viewModelScope.launch {
            val result = getAllIngredients()
            if (!result.isNullOrEmpty()) {
                ingredientModel.postValue(result[0])
            }
        }
    }
}