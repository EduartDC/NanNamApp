package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.ApiResponse
import com.example.nannamapp.data.model.JsonResult
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){
    val loginModel = MutableLiveData<ApiResponse?>()
    var loginUseCase = LoginUseCase()

    fun onCreate(login: Login) {
        viewModelScope.launch {
            val result = loginUseCase(login)
            loginModel.postValue(result)
        }
    }
}