package com.example.nannamapp.data

import com.example.nannamapp.data.model.ApiResponse
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.RegisterProvider
import com.example.nannamapp.data.model.User
import com.example.nannamapp.data.model.UserProvider
import com.example.nannamapp.data.network.LoginService

class LoginRepository {
    private val api = LoginService()

    suspend fun loginUser(login: Login): ApiResponse? {
        val response = api.loginUser(login)
        if (response != null) {
            LoginProvider.login = response?.value
        }
        return response
    }

    suspend fun getUserInfo(idUser: String): User? {
        val response = api.getUser(idUser)
        if (response != null) {
            LoginProvider.user.firstname = response.firstname
            LoginProvider.user.lastname = response.lastname
            LoginProvider.user.password = response.password
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