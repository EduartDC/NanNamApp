package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namnam.data.model.RecipeDomain
import com.example.nannamapp.domain.GetCookBookUseCase
import kotlinx.coroutines.launch

class CookBookViewModel: ViewModel() {
    val cookBookModel = MutableLiveData<List<RecipeDomain>?>()
    var getCookbookUseCase = GetCookBookUseCase("123")
    fun onCreate() {
        viewModelScope.launch {
            try {
                val result = getCookbookUseCase()
                cookBookModel.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
                // Manejar la excepción, por ejemplo, mostrar un mensaje de error
            }
        }
    }
}