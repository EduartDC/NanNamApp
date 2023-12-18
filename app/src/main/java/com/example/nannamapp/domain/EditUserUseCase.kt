package com.example.nannamapp.domain

import com.example.nannamapp.data.LoginRepository
import com.example.nannamapp.data.model.User
import com.google.gson.stream.MalformedJsonException

class EditUserUseCase (){
    private val repository = LoginRepository()
    suspend operator fun invoke(user: User): String? {
        return try {
            repository.editUser(user)
        } catch (e: MalformedJsonException) {
            e.printStackTrace()
            null
        }
    }
}