package com.example.nannamapp.domain

import com.example.nannamapp.data.LoginRepository
import com.example.nannamapp.data.model.JsonResult
import com.example.nannamapp.data.model.Login

class LoginUseCase {
    private val repository = LoginRepository()

    suspend operator fun invoke(login: Login): JsonResult? = repository.loginUser(login)
}