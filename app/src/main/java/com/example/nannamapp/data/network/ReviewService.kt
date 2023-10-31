package com.example.nannamapp.data.network

import android.util.Log
import com.example.namnam.data.network.APIClient
import com.example.nannamapp.core.RetrofitHelper
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.ReviewDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getReview(idRecipe : String): Pair<Int, List<ReviewDomain>>{
        return withContext(Dispatchers.IO) {
            var code = 0
            var body  : List<ReviewDomain>
            try {
                val response = retrofit.create(APIClient::class.java).getReview(idRecipe)
                code = 200
                body = response ?: emptyList()
                Pair(code,body)
            }catch (e : Exception){
                code = 500
                Pair(code, emptyList())
            }

        }
    }

    suspend fun setReview(newReview : ReviewDomain): Int {
        return withContext(Dispatchers.IO) {
            var code : Int = 0
            try {
                val response = retrofit.create(APIClient::class.java).setReview(newReview)
                // response.errorBody()?.string()?.let { Log.d("Mensaje de la api", it) }
                if (response.isSuccessful)
                    code = 200
            } catch (e: Exception) {
                e.printStackTrace()
                code = 500
            }
            code
        }
    }
}