package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.ApiResponse
import com.example.nannamapp.data.model.User
import com.example.nannamapp.domain.GetRecipeUseCase
import com.example.nannamapp.domain.GetUserUseCase
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    val userModel = MutableLiveData<User?>()
    var idUser : String = ""

    val getUserUseCase : GetUserUseCase by lazy { GetUserUseCase(idUser) }

    fun getRecipe(){
        viewModelScope.launch {
            var result = getUserUseCase()
            userModel.postValue(result)
        }
    }
}