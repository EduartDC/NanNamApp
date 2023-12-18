package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.ApiResponse
import com.example.nannamapp.data.model.User
import com.example.nannamapp.domain.EditUserUseCase
import com.example.nannamapp.domain.GetRecipeUseCase
import com.example.nannamapp.domain.GetUserUseCase
import com.example.nannamapp.domain.RegisterUseCase
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    val userModel = MutableLiveData<User?>()
    var idUser : String = ""
    val user= User("","","","", "")
    val updateUserUseCase = EditUserUseCase()

    val getUserUseCase : GetUserUseCase by lazy { GetUserUseCase(idUser) }
    fun getUser(){
        viewModelScope.launch {
            var result = getUserUseCase()
            userModel.postValue(result)
        }
    }
    
    fun updateUser(user: User){
        viewModelScope.launch {
            val result = updateUserUseCase(user)
            if (!result.isNullOrEmpty()) {
                userModel.postValue(result)
            }
        }
    }
}

private fun Any.postValue(result: String) {

}
