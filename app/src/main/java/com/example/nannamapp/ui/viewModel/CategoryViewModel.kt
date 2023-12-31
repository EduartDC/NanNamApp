package com.example.nannamapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namnam.data.model.Category

import com.example.nannamapp.domain.GetCategoriesUseCase
import kotlinx.coroutines.launch



class CategoryViewModel : ViewModel() {
    val categoryModel = MutableLiveData<Category>()
    var getAllCategoriesUseCase = GetCategoriesUseCase()

    fun onCreate() {
         try {
             viewModelScope.launch {
                 val result = getAllCategoriesUseCase()
                 if (!result.isNullOrEmpty()) {
                     categoryModel.postValue(result[0])
                 }
             }
         } catch (e: Exception) {
             Log.e("ERROR en CategoryViewModel Impreso", "Mensaje de error descriptivo", e)
         }

    }

}