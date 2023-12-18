package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.User
import com.example.nannamapp.domain.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    val registerModel = MutableLiveData<String?>()
    val registerUseCase = RegisterUseCase()

    fun onCreate(user: User) {
        viewModelScope.launch {
            val result = registerUseCase(user)

            if (!result.isNullOrEmpty()) {
                registerModel.postValue(result)
            }
        }
    }
}