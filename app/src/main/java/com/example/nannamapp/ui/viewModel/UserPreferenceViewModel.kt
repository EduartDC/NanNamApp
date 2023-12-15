package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.data.model.SetPreferenceResponse
import com.example.nannamapp.domain.GetReviewsUseCase
import com.example.nannamapp.domain.GetUserPreferenceUseCase
import com.example.nannamapp.domain.SetReviewUseCase
import com.example.nannamapp.domain.SetUserPreferenceUseCase
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

    var httpCodeSetUserPreference: Int = 0
    lateinit var setPreferenceResponse: SetPreferenceResponse
    lateinit var  setUserPreferenceUseCase: SetUserPreferenceUseCase
    val setUserPreferenceViewModel = MutableLiveData<Int>()

    fun setNewUserPreference() {
        viewModelScope.launch {
            setUserPreferenceUseCase = SetUserPreferenceUseCase()
            val result = setUserPreferenceUseCase(setPreferenceResponse) // Llama al caso de uso
            //if (!result.isNullOrEmpty()) {
            setUserPreferenceViewModel.postValue(result)
            httpCodeSetUserPreference = result
            //}
        }
    }


}