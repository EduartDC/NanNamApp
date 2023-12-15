package com.example.nannamapp.domain

import com.example.nannamapp.data.LoginRepository
import com.example.nannamapp.data.model.User

class GetUserUseCase (private val idUser : String){
    private val repository = LoginRepository()
    suspend operator fun invoke() : User? = repository.getUserInfo(idUser)
}