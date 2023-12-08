package com.example.nannamapp.ui.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.domain.GetCookBookUseCase
import kotlinx.coroutines.launch

class CookBookViewModel: ViewModel() {
    var httpCodeRecipe: Int = 0
    val cookBookModel = MutableLiveData<Int>()

    //obtener el id del usuario
    var getCookbookUseCase = GetCookBookUseCase("123")
    fun onCreate() {
        viewModelScope.launch {
            try {
                val result = getCookbookUseCase()
                httpCodeRecipe = result
                cookBookModel.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}