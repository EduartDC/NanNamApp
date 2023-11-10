package com.example.nannamapp.domain

import com.example.nannamapp.data.LoginRepository
import com.example.nannamapp.data.model.User

class RegisterUseCase {
    private val repository = LoginRepository()

    suspend operator fun invoke(user: User): String? = repository.registerUser(user)
}