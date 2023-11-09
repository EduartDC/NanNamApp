package com.example.nannamapp.data.network

import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import com.example.nannamapp.data.model.GetPreferenceResponse
import com.example.nannamapp.data.model.ReviewDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPreferenceService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getUserPreference(idUser: String): Pair<Int, GetPreferenceResponse> {
        return withContext(Dispatchers.IO) {

            var code = 0
            var body: GetPreferenceResponse
            try {
                val response = retrofit.create(APIClient::class.java).getUserPreference(idUser)
                code = response.code()
                body = response.body() ?: GetPreferenceResponse()
                Pair(code, body)
            } catch (e: Exception) {
                code = 500
                Pair(code, GetPreferenceResponse())
            }

        }
    }






}