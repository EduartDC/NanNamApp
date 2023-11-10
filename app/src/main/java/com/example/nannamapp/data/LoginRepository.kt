package com.example.nannamapp.data

import com.example.nannamapp.data.model.JsonResult
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.RegisterProvider
import com.example.nannamapp.data.model.User
import com.example.nannamapp.data.network.LoginService

class LoginRepository {
    private val api = LoginService()

    suspend fun loginUser(login: Login): JsonResult? {
        val response = api.loginUser(login)
        if (response != null) {
            LoginProvider.login = response
        }
        return response
    }

    suspend fun registerUser(user: User): String? {
        val response = api.registerUser(user)
        if (!response.isNullOrEmpty()) {
            RegisterProvider.register = response
        }
        return null
    }
}