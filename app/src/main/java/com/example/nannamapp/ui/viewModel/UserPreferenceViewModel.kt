package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.domain.GetReviewsUseCase
import com.example.nannamapp.domain.GetUserPreferenceUseCase
import kotlinx.coroutines.launch

class UserPreferenceViewModel : ViewModel(){
    var httpCodegetUserPreference : Int = 0
    var idUser : String = ""
    val getReviewUseCase : GetUserPreferenceUseCase by lazy { GetUserPreferenceUseCase(idUser ) }
    val getUserPreferenceViewModel = MutableLiveData<Int>()
    fun getUserPreferences(){
        viewModelScope.launch {
            var result = getReviewUseCase()
            httpCodegetUserPreference = result
            getUserPreferenceViewModel.postValue(result)

        }
    }
}