package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.domain.GetCookBookUseCase
import kotlinx.coroutines.launch

class CookBookViewModel: ViewModel() {
    val cookBookModel = MutableLiveData<Recipe>()
    val getCookbookUseCase = GetCookBookUseCase("123")
    fun onCreate() {
        viewModelScope.launch{
            val result = getCookbookUseCase()
            if (!result.isNullOrEmpty()) {
                cookBookModel.postValue(result[0])
            }
        }
    }
}