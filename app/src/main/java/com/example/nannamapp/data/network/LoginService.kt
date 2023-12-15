package com.example.nannamapp.data.network

import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import com.example.nannamapp.data.model.ApiResponse
import com.example.nannamapp.data.model.JsonResult
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun loginUser(login: Login): ApiResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(APIClient::class.java).loginUser(login)
            response.body()
        }
    }

    suspend fun getUser(idUser: String): User? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(APIClient::class.java).getUserInfo(idUser)
            response.body()
        }
    }

    suspend fun registerUser(user: User): String? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(APIClient::class.java).registerUser(user)
            response.body()
        }
    }
}