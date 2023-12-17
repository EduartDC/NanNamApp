package com.example.nannamapp.domain

import com.example.nannamapp.data.LoginRepository
import com.example.nannamapp.data.model.User
import com.google.gson.stream.MalformedJsonException

class RegisterUseCase {
    private val repository = LoginRepository()

    suspend operator fun invoke(user: User): String? {
        return try {
            repository.registerUser(user)
        } catch (e: MalformedJsonException) {
            // Manejo de la excepción MalformedJsonException
            e.printStackTrace() // o utiliza tu propio método de registro de errores

            // Puedes agregar aquí el código para manejar la excepción según tus necesidades.
            // Por ejemplo, puedes mostrar un mensaje de error al usuario o realizar alguna acción específica.
            null // En este ejemplo, se devuelve null en caso de excepción, pero puedes adaptarlo según tus necesidades.
        }
    }
}
